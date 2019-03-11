# Openshift Egress Router 설정



### 개요

- Pod 는 클러스터 노드에 고정적으로 위치하지 않으므로 Pod 가 외부로 나갈 때 nat 로 나가는 ip가 일정하지 않다. 하지만 Openshift Egress Router 를 통해 나갈경우 고정된 IP를 갖고 외부로 접근할 수 있다. 
- Openshift Egress Router Pod 는 타겟 서버로 나가는 outgoing 트래픽 전송을 담당하고 그 때 특정 소스 IP  주소를 가진다. 어플리케이션은 외부 실제 서비스를 직접 호출하는 것이 아니라  Egress Router 서비스를 호출하게 되고 이는 Egress Router Pod에 의해 재 전달되는 것이다.
- NodeSelector를 가지고 Namespace 단위로  Router를 배포하고 이 때 이미 할당된 static IP를 가지게 된다.
- Egress Router 가 모든 outgoing 트래픽을 위해 사용하는 것은 권고하지 않는다.  많은 Egress Router 의 생성은 네트워크 리소스의 제약을 가져온다.
- Egress Router 는 primary network interface에 IP를 추가하는 하는 방식이다. 이때의  IP는 노드 IP와 동일한  Subnet을 가져야 한다.



###  사전 준비

- 클러스터 관리자는 노드별 사용 가능한  static IPs를 할당한다.

```bash
$ oc patch hostsubnet <node_name> -p \
'{"egressIPs": ["<IP_address_1>", "<IP_address_2>"]}'
```

```bash
$ oc patch hostsubnet herasoo-dmz-infra-wokernode1 -p \
  '{"egressIPs": ["192.168.12.99", "192.168.12.100", "192.168.12.101"]}'
```

- 클러스터 관리자는 Namespace 별 사용 가능한 static IPs를 할당한다. 이는 특정 Namespace에서 나가는 트래픽을 쉽게 구분시켜 주는 장점이 있다.  

```bash
$ oc patch netnamespace <project_name> -p '{"egressIPs": ["<IP_address>"]}'
```

```bash
$ oc patch netnamespace herasoo -p '{"egressIPs": ["192.168.12.99"]}'
```

- 위와 같이 설정한다면 herasoo Namespace에 생성되는 Egress Router Pod는 herasoo-dmz-infra-wokernode1 Node에 배치되고 그 때  static IP 192.168.12.99를 가지게 된다. 이로써 Node별 static IPs, Namespace별 static IPs  관리되어 효율적인 관리와 가시적인 모니터링이 가능하다.



### Sample Router

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: deployment-egress-route-1
spec:
  selector:
    matchLabels:
      app: egress-route-1
  replicas: 1
  template:
    metadata:
      labels:
        app: egress-route-1
      annotations:
        pod.network.openshift.io/assign-macvlan: "true"
    spec:
      initContainers:
      - name: egress-router
        image: ktis-bastion01.container.ipc.kt.com:5000/openshift3/ose-egress-router:v3.11.16
        imagePullPolicy: Always
        env:
        - name: EGRESS_SOURCE
          value: 192.168.12.99
        - name: EGRESS_GATEWAY
          value: 192.168.12.1
        - name: EGRESS_DESTINATION
          #value: 203.0.113.25
          value: |
            80   tcp 203.0.113.25
            443	 tcp 203.0.113.25	
            8080 tcp 203.0.113.26 80
            8443 tcp 203.0.113.26 443
            #203.0.113.27
        - name: EGRESS_ROUTER_MODE
          value: init
        securityContext:
          privileged: true
      containers:
      - name: egress-router-wait
        image: ktis-bastion01.container.ipc.kt.com:5000/openshift3/ose-pod:v3.11.16
      #nodeSelector:
        #site: springfield-1
      nodeName: herasoo-dmz-infra-wokernode1
      restartPolicy: Always
```

- `replicas: 1` 1이어야만 해당 소스IP 192.168.12.99를 가질 수 있다.

- `nodeName: herasoo-dmz-infra-wokernode1` 해당 노드에 192.168.12.99 static IP가 할당 되어 있다.

- `name: EGRESS_SOURCE value: 192.168.12.99` Router의 소스IP가 된다.

- `name: EGRESS_GATEWAY value: 192.168.12.1` herasoo-dmz-infra-wokernode1 의 GW IP정보와 같아야 한다.

- `name: EGRESS_DESTINATION`

  - redirect traffic rule을 작성한다.

  ```yaml
  80   tcp 203.0.113.25	  # Router에 80으로 들어오면 tcp 203.0.113.25 80으로 보낸다.
  443	 tcp 203.0.113.25	  # Router에 443으로 들어오면 tcp 203.0.113.25 443으로 보낸다.
  8080 tcp 203.0.113.26 80  # Router에 8080으로 들어오면 tcp 203.0.113.26 80으로 보낸다.
  8443 tcp 203.0.113.26 443 # Router에 8443으로 들어오면 tcp 203.0.113.26 443으로 보낸다.
  203.0.113.27			  # fallback IP로 위에 정해지지 않는 포트로 들어왔을 때 203.0.113.27의 포트로 보낸다. 설정되지 않았다면 Redirect가 거절된다.
  ```

- `value: init` initContainer 형태로 동작한다.



### Sample Router Service

```yaml
apiVersion: v1
kind: Service
metadata:
  name: service-egress-route-1
spec:
  ports:
  - name: http-remote1	 # 192.168.12.99를 소스IP로 가지고 203.0.113.25 80으로 전송된다.
    port: 80
  - name: https-remote1	 # 192.168.12.99를 소스IP로 가지고 203.0.113.25 443으로 전송된다.
    port: 443
  - name: http-remote2	 # 192.168.12.99를 소스IP로 가지고 203.0.113.26 80으로 전송된다.
    port: 8080
  - name: https-remote2	 # 192.168.12.99를 소스IP로 가지고 203.0.113.26 443으로 전송된다.
    port: 8443
  type: ClusterIP
  selector:
    app: egress-route-1
```



