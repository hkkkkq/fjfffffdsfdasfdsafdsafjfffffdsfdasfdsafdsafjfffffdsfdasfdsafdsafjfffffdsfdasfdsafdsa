
# 개정이력

|    날짜    | 변경내용  | 작성자 | 비고 |
| :--------: | :-------: | :----: | :--: |
| 2019.04.26 | 최초 작성 | 전원호 |      |
|            |           |        |      |
|            |           |        |      |



# Audience

- Istio를 적용하여 Service mesh 밖으로 나가는 트래픽을 정해진 노드를 통해서만 가능하도록 설정하고 싶은 자



# 개요

일반적으로 Istio의 환경에서 Service Mesh 밖의 서비스 호출은 Client Pod 내 side-car에 의해 Directly하게 이루어진다. 물론 이를 가능하게 하기 위해서는 traffic policy 나 IPrange 변경 설정이 필요하고 주로 ServiceEntry 오브젝트를 이용한 Service registry에 등록 방식으로 가능하다. Istio의 경우 기본적으로 Serivice Mesh 밖의 외부 서비스 호출이 제약되기 때문이다.

이 장에서는 외부 서비스의 호출을 Directly가 아닌 Indirectly하게 Egress gateway를 통하여 호출하는 방법을 설명한다. Egress gateway도 Ingress gateway와 마찬가지로 Service Mesh의 Edge 영역에 위치하며 Load balancer 역할을 담당하고 monitoring 및 routing ruler과 같은 istio feature를 활용할 수 있다.



# Use case

- 일부 조직의 경우 강력한 보안 요구사항에 의해 Service Mesh 밖의 외부 서비스 호출을 정해진 노드에서만 가능하게 요구할 수 있다. 이때 해당 노드는 클러스터 내 Application이 배치되는 노드와는 구분되며 다른 노드에 비해 모니터링을 철저히 수행해야 한다.

- Application이 배치된 노드에 Public IPs가 없어 Internet 연결이 불 가능한 경우 Egress gateway가 존재하는 Node의 Public IPs를 통하여 Internet 연결을 통해 외부 서비스와 통신이 가능하다.



# 사전 준비

- istio Egress-gateway가 설치되어 있는지 확인한다.

```bash
$ kubectl get pod -l istio=egressgateway -n istio-system
```

- 우리 환경에서는 ktis-infra Node에 설치되어 있음을 확인할 수 있다. 즉 Egress-gateway 설정을 하면 Infra Node에 설치된 Egress-gateway에 의해 해당 Node의 IP로 외부 서비스를 Proxy한다.



# istio Egress-gateway 설정

1. 먼저, ServiceEntry 오브젝트를 활용하여 Service Registry에 등록하여 client side-car를 통해 Directly 외부 호출이 가능하도록 한다.

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: ServiceEntry
metadata:
  name: cnn
spec:
  hosts:
  - edition.cnn.com
  ports:
  - number: 80
    name: http-port
    protocol: HTTP
  resolution: DNS
```



2. (Egress) Gateway와 DestinationRule을 작성한다.

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: Gateway
metadata:
  name: istio-egressgateway
spec:
  selector:
    istio: egressgateway
  servers:
  - port:
      number: 80
      name: http
      protocol: HTTP
    hosts:
    - edition.cnn.com
---
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: egressgateway-for-cnn
spec:
  host: istio-egressgateway.istio-system.svc.cluster.local
  subsets:
  - name: cnn
```



3. VirtualService를 작성한다.

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: direct-cnn-through-egress-gateway
spec:
  hosts:
  - edition.cnn.com
  gateways:
  - istio-egressgateway
  - mesh # applies to all the sidecars in the mesh
  http:
  - match:
    - gateways:
      - mesh
      port: 80
    route:
    - destination:
        host: istio-egressgateway.istio-system.svc.cluster.local
        subset: cnn
        port:
          number: 80
      weight: 100
  - match:
    - gateways:
      - istio-egressgateway
      port: 80
    route:
    - destination:
        host: edition.cnn.com
        port:
          number: 80
      weight: 100
```



4. Application에서 호출하기

ServiceEntry에 등록한 `edition.cnn.com` 로 호출하면 된다.

```bash
$ kubectl exec -it $SOURCE_POD -c sleep -- curl -sL -o /dev/null -D - http://edition.cnn.com/politics
HTTP/1.1 301 Moved Permanently
...
location: https://edition.cnn.com/politics
...

HTTP/1.1 200 OK
Content-Type: text/html; charset=utf-8
...
Content-Length: 151654
...
```

