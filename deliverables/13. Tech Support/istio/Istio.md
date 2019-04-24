# Istio



#### 환경

kubenetes 1.11

openshift 3.11 / istio 1.0.4



#### Istio 주요 기능

- 서비스의 하위 그룹 subset을 구분지어 Routing
- Fault 및 Delay injection
- **weight를 두어 Traffic shifting**
- **Request timeout (default 15s)**
- Request retry
- **Ingress / Egress**
- **Circuit breaking** (Connection Pool, Outlier detection)
- Mirroring



#### Sidecar injection

>  oc 명령과 kubectl 명령은 기본적으로 동일하게 동작한다. istio 기능을 활용하는데 몇 가지 차이가 있음



1. 권한 및 인증 필요 (안하면 Pod 생성이 안됨)

```bash
oc adm policy add-scc-to-user anyuid -z default -n herasoo $ oc adm policy add-scc-to-user privileged -z default -n herasoo
```



2. Envoy(Side-car) Injection

- Manual Injection (동일)

```bash
oc apply -f <(istioctl kube-inject -f Deployment.yml) -n herasoo
```

- Auto Injection (Annotations 항목 추가 방법)

```bash
kubectl label ns herasoo istio-injection=enabled
```

위의 Namespace Label 방식 지원하지 않는다. 아래와 같이 Annotations 방법을 oc에서는 Auto Injection이하고 한다.

```yaml
apiVersion: extensions/v1beta1
kind: Deployment
metadata:
  name: productpage-v1
spec:
  replicas: 1
  template:
    metadata:
      labels:
        app: productpage
        version: v1
      annotations:
        sidecar.istio.io/inject: "true"
```



#### DestinationRule 작성

- Envoy proxy간 tls 통신을 활성화하도록 설치/설정하였다면 다음을 추가하여 사용하여야 한다. 그렇지 않으면 503 서버 통신 에러가 발생한다.
- trafficPolicy 부분

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: DestinationRule
metadata:
  name: details
spec:
  trafficPolicy:
    tls:
      mode: ISTIO_MUTUAL

```



#### Openshift Route 객체를 이용한 istio-ingressgateway 사용

- Openshift Route 객체는 Kubenetes의 Ingress의 확장 버전으로 이해하면 된다.
- host 정보가 존재하는 Ingress를 생성하면 path 개수만큼 Route 객체가 자동 생성되는 것을 확인할 수 있다.

```yaml
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-micro-svc
spec:
  rules:
   - host: micro-svc.com
     http:
      paths:
      - path: /svc1
        backend:
          serviceName: service-micro-svc-1
          servicePort: 80
      - path: /svc2
        backend:
          serviceName: service-micro-svc-2
          servicePort: 80

```

```bash
[bsscoe@ktis-master01 micro-svc-example1]$ k get ing
NAME                HOSTS           ADDRESS   PORTS     AGE
ingress-micro-svc   micro-svc.com             80        2m
[bsscoe@ktis-master01 micro-svc-example1]$ k get route
NAME                      HOST/PORT       PATH      SERVICES              PORT      TERMINATION   WILDCARD
ingress-micro-svc-5rtdj   micro-svc.com   /svc1     service-micro-svc-1   8080                    None
ingress-micro-svc-w4czd   micro-svc.com   /svc2     service-micro-svc-2   8090                    None

```

- Istio 환경에서, 즉 Pod에 Envoy-proxy가 주입된 이후에는 Kubenetes의 환경처럼 console에서 cluster IP로 Pod나 Service 객체에 직접 호출이 불 가능하다는 것이다. Istio에서는 모든 네트워크 호출은 Envoy-proxy에 의해 이루어지니 당연한 이야기일 수 있다. 
- Pod에 Envoy-proxy가 주입된 이후에는 Gateway와 ServiceEntry 객체를 통해 클러스터 내외부 서비스 호출이 가능하다.
- Istio 환경에서 Inbound 경우 istio-system 네임스페이스의 istio-ingressgateway Pod와 Service에 의해 호출이 이루어진다. 해당 클러스터 내 모든 인입 네트워크가 해당 Pod와 Service에 의해 이루어진다고 이해하면 된다.
- 다음과 같이 작성하여 사용하면 된다.

```yaml
apiVersion: networking.istio.io/v1alpha3
kind: Gateway
metadata:
  name: bookinfo-gateway
spec:
  selector:
    istio: ingressgateway # use istio default controller
  servers:
  - port:
      number: 80
      name: http
      protocol: HTTP
    hosts:
    #- "*"
    - "bookinfo-productpage.container.ipc.kt.com"
---
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  name: bookinfo
spec:
  hosts:
  #- "*"
  - "bookinfo-productpage.container.ipc.kt.com"
  gateways:
  - bookinfo-gateway
  http:
  - match:
    - uri:
        exact: /productpage
    - uri:
        exact: /login
    - uri:
        exact: /logout
    - uri:
        prefix: /api/v1/products
    route:
    - destination:
        host: productpage
        port:
          number: 9080
```

- 위 내용 중 Gateway, VirtualService의 hosts 부분을 명시적으로 작성하는 것이 좋다. 결국 istio-ingressgateway 서비스에 적용되는 것이기에 추후 혼돈에 의한 오 동작의 가능성을 줄일 수 있다.

```yaml
apiVersion: route.openshift.io/v1
kind: Route
metadata:
  name: route-bookinfo-productpage
  namespace: istio-system
spec:
  host: bookinfo-productpage.container.ipc.kt.com
  port:
    targetPort: http2
  #path: "/svc1"
  to:
    kind: Service
    name: istio-ingressgateway
    weight: 100
  wildcardPolicy: None
```

- Route 객체

```yaml
apiVersion: route.openshift.io/v1
kind: Route
metadata:
  name: route-bookinfo-productpage
  namespace: istio-system
spec:
  host: bookinfo-productpage.container.ipc.kt.com
  port:
    targetPort: http2
  #path: "/svc1"
  to:
    kind: Service
    name: istio-ingressgateway
    weight: 100
  wildcardPolicy: None
```

- Route 객체의 경우 to Service가 존재하는 네임스페이스에 배포되어야 한다.
- 필요한대로 host에 맞추어 여러 Route 객체 생성이 가능하다. 그러나 Istio 환경에서 to Service는 istio-ingressgateway으로 동일하다.



#### istio-ingressgateway가 정상 동작하지 않을 경우

- `kubectl get po -n istio-system` 을 통해 해당 Pod가 Running 상태인지 확인한다.
- `istioctl proxy-status` Pilot과 Envoy의 상태 정보를 출력한다. istio-ingressgateway Pod의 상태를 확인한다.
  - SYNCED: 정상
  - NOT SENT: Pilot에서 Envoy로 보낼 데이터가 없다는 뜻이다. 설정한 정보가 없어서 해당 상태이면 정상이나 설정 변경을 했음에도 해당 상태라면 비 정상 상태이다.
  - STALE: Pilot은 Envoy로 데이터를 보냈는데 Envoy는 수신하지 못한 네트워크 이슈 또는 버그 상태이다.
- `istioctl proxy-status istio-ingressgateway-dbbc584d7-gdkjd.istio-system` 해당 Pod의 상태 조회 시 다음과 같은 결과가 정상이다.

```bash
Clusters Match
Listeners Match
Routes Match
```

- 더불어 `istioctl proxy-config route istio-ingressgateway-dbbc584d7-gdkjd -n istio-system -o json` 해당 명령어를 통해 istio-ingressgateway에 등록된 "virtualHosts" 정보를 확인할 수 있다. 즉 Gateway, VirtualService에 설정한 Route 정보가 포함되어 있어야 정상적인 상태이다.