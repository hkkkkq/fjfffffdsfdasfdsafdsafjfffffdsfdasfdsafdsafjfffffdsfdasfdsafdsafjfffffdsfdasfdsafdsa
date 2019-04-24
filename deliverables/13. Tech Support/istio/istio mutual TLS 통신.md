# istio mutual TLS 통신


### 개정이력

| 날짜       | 변경내용  | 작성자 | 비고 |
| ---------- | --------- | ------ | ---- |
| 2019.02.14 | 최초 작성 | 송양종 |      |
| 2019.03.28 | 내용 보완 | 전원호 |      |



### 1. 개념

istio 에서는 envoy 를 통하는 트래픽은 자동으로 TLS 암호화한다. 이는 istio에서는 istio-proxy간 통신을 하며 TLS 암호화 통신을 하는게 기본 설정임을 나타낸다. 그러므로 non-istio 서비스(envoy가 없는 서비스) 와의 통신은 불가능하다. 추가적인 설정으로 이를 가능하게 할 수 있다.

아래의 명령으로 현재 지정된 정책을 조회 할 수 있음.

```bash
# 정책(policy) 조회   
$ kubectl get policies.authentication.istio.io --all-namespaces   
$ kubectl get meshpolicies.authentication.istio.io --all-namespaces   
$ kubectl get destinationrules.networking.istio.io --all-namespaces
```
정책은 클러스터 전체, 특정 namespace만, 특정 service만, 특정service-port 이렇게 4가지 형태로 적용시킬 수 있다. **해당 정책 설정은 upstream server 설정으로 이해하면 된다.**

그중 클러스터 전체(모든 메쉬) 정책을 확인하는 방법은 아래와 같다.
```yaml
# mesh 정책 조회
$ kubectl get meshpolicy default -o yaml
apiVersion: authentication.istio.io/v1alpha1
kind: MeshPolicy
metadata:
  creationTimestamp: 2019-01-09T04:53:55Z
  generation: 1
  labels:
    app: istio-security
    chart: security-1.0.4
    heritage: Tiller
    maistra-version: 0.5.0
    release: istio-1.0.4
  name: default
  resourceVersion: "5171547"
  selfLink: /apis/authentication.istio.io/v1alpha1/meshpolicies/default
  uid: 91925851-13ca-11e9-91e8-fa163edb5d9f
spec:
  peers:
  - mtls: {}
```

mtls 의 항목을 보면 아무 설정값이 없으므로 기본값인 mode: STRICT 이며 이는 반드시 Mutual TLS 통신만 가능하다. 하지만 아래와 같이 mode: PERMISSIVE 옵션으로 non-istio 서비스와 통신 가능하게 할 수 있다. PERMISSIVE 설정은 암호화 통신과 plain-text 둘 다 허용하게 만드는 설정이다.

```
spec:
  peers:
  - mtls:
      mode: PERMISSIVE
```




### 2. defualt값 설정
기본 mutual TLS를 설정(permissive or strict) 하는 인증 정책 설정 (defualt값 설정)


#### 1) 메쉬 정책 적용 - 모든 메쉬에 적용됨.

```yaml
cat <<EOF | kubectl apply -f -
apiVersion: "authentication.istio.io/v1alpha1"
kind: "MeshPolicy"
metadata:
  name: "default"
spec:
  peers:
  - mtls: {}
EOF
---
cat <<EOF | kubectl apply -f -
apiVersion: "networking.istio.io/v1alpha3"
kind: "DestinationRule"
metadata:
  name: "default"
  namespace: "default"
spec:
  host: "*.local"
  trafficPolicy:
    tls:
      mode: ISTIO_MUTUAL
EOF

```
위 정책은 mtls 에 기술된 내용이 없으므로 기본 값인 STRICT 이며 메시의 모든 workload가 TLS를 사용하여 암호화 된 요청 만 수락하도록 지정한다. 또한 name 은 default 이며 target 이 명시되어 있지 않아 전체메쉬에 적용된다.


#### 2) nsmespace 정책 적용 - 특정 nsmespace에 적용됨

```yaml
cat <<EOF | kubectl apply -f -
apiVersion: "authentication.istio.io/v1alpha1"
kind: "Policy"
metadata:
  name: "default"
  namespace: "foo"       # foo namespace 만 적용
spec:
  peers:
  - mtls: {}
EOF
---
cat <<EOF | kubectl apply -f -
apiVersion: "networking.istio.io/v1alpha3"
kind: "DestinationRule"
metadata:
  name: "default"
  namespace: "foo"
spec:
  host: "*.foo.svc.cluster.local"   
  trafficPolicy:
    tls:
      mode: ISTIO_MUTUAL
EOF
```
위 정책은 foo namespace 만 적용된다.



####  3)  Service 정책 적용

```yaml
cat <<EOF | kubectl apply -n bar -f -
apiVersion: "authentication.istio.io/v1alpha1"
kind: "Policy"
metadata:
  name: "httpbin"
spec:
  targets:
  - name: httpbin
  peers:
  - mtls: {}
EOF
---
cat <<EOF | kubectl apply -n bar -f -
apiVersion: "networking.istio.io/v1alpha3"
kind: "DestinationRule"
metadata:
  name: "httpbin"
spec:
  host: "httpbin.bar.svc.cluster.local"
  trafficPolicy:
    tls:
      mode: ISTIO_MUTUAL
EOF
```
위 정책은 httpbin 이라는 service 에만 적용된다.  
ISTIO_MUTUAL 옵션으로 Istio는 키와 인증서의 경로를 설정한다.

 

#### 4) Service / port 적용 정책적용

```yaml
cat <<EOF | kubectl apply -n bar -f -
apiVersion: "authentication.istio.io/v1alpha1"
kind: "Policy"
metadata:
  name: "httpbin"
spec:
  targets:
  - name: httpbin
    ports:
    - number: 1234
  peers:
  - mtls: {}
EOF
---
cat <<EOF | kubectl apply -n bar -f -
apiVersion: "networking.istio.io/v1alpha3"
kind: "DestinationRule"
metadata:
  name: "httpbin"
spec:
  host: httpbin.bar.svc.cluster.local
  trafficPolicy:
    tls:
      mode: DISABLE
    portLevelSettings:
    - port:
        number: 1234
      tls:
        mode: ISTIO_MUTUAL
EOF
```

 

### 3. non-istio => istio서비스와 통신 설정
istio 서비스간에는 envoy 를 통한 TLS 통신이 기본이지만 기타 여러가지 사유로 non-istio 서비스(envoy가 없는 서비스)와 통신을 해야 할 경우가 발생한다. 예를 들면, istio 시스템을 처음 도입시 서비스 중단 없이 istio 시스템으로 전환하는 경우가 있을 수 있다.  기타 여러 상황을 고려하여 non-istio 서비스와의 통신을 허용하는 방법을 제공한다.  


#### 1) istio Policy 의 두가지 mode
PERMISSIVE mode : non-istio 서비스의 트래픽을 허용함.
STRICT mode : mutual TLS traffic 만 허용(non-istio 서비스의 트래픽을 허용하지 않음)


#### 2) 테스트 시나리오
1) istio 서비스(userlist) 실행
2) non-istio 서비스 실행(curl 명령가능한 mypage-frontend 이용)
3) 2번에서 1번으로 curl 명령 수행 : 접속이 불가능 - 확인완료
4) policy 를 permissive 로 변경
5) 2번에서 1번으로 curl 명령 수행 : 접속가능  - 확인완료


#### 3) userlist policy

```yaml
cd /home/bsscoe/song/userlist
$ cat > 16.userlist-istio-policy-destrule.yaml
apiVersion: "authentication.istio.io/v1alpha1"
kind: "Policy"
metadata:
  name: "userlist-istio-policy"
spec:
  targets:
  - name: userlist-svc            # service
  peers:
  - mtls:
      mode: PERMISSIVE   # accept both mutual TLS and plain text traffic
      # mode: STRICT     # lock down to only mutual TLS
---
apiVersion: "networking.istio.io/v1alpha3"
kind: "DestinationRule"
metadata:
  name: "userlist-svc"
spec:
  host: "userlist-svc.dev-song.svc.cluster.local"
  trafficPolicy:
    tls:
      mode: ISTIO_MUTUAL
---

# mode 변경해가면서 테스트 실시
$ ks apply -f 16.userlist-istio-policy-destrule.yaml

```

 

#### 4) 결론

non-istio => istio서비스 통신의 경우 Client side에는 envoy가 없기 때문에 tls 암호화 데이터 생성이 불가능하다. upstream에 대한 mode: PERMISSIVE 정책 설정으로 가능하다.



### 4. istio => non-istio서비스와 통신 설정

#### 1) 결론

istio => non-istio서비스 통신의 경우 Server side envoy가 없기 때문에 tls 암호화 데이터를 처리할 능력이 없다. 아래와 같이 DestinationRule 설정을 통해 통신 가능하다.

```yaml
apiVersion: "networking.istio.io/v1alpha3"
kind: "DestinationRule"
metadata:
  name: "httpbin-legacy"
spec:
  host: "httpbin.legacy.svc.cluster.local"
  trafficPolicy:
    tls:
      mode: DISABLE
```



