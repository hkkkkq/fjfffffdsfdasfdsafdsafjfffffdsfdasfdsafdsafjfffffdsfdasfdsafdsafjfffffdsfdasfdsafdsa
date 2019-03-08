# Openshift Egress Router 설정



### 개요

- Openshift Egress Routers는 타겟 서버로 나가는 outgoing 트래픽 전송을 담당하고 그 때 특정 소스 IP  주소를 가진다.
- NodeSelector를 가지고 Namespace 단위로  Router를 배포하고 이 때 이미 할당된 static IP를 가지게 된다.
- Egress Routers 가 모든 outgoing 트래픽을 위해 사용되어서는 안 된다. 많은 Egress Routers 생성은 네트워크 리소스의 제약을 가져오기 때문이다.
- Egress Routers 는 primary network interface에 IP를 추가하는 하는 방식이다. 이때의  IP는 노드 IP와 동일한  Subnet을 가져야 한다.
- 어플리케이션은 외부 실제 서비스를 직접 호출하는 것이 아니라  Egress Router 서비스를 호출하게 된다.



###  사전 준비

- 클러스터 관리자는 노드별 사용 가능한  static IPs를 할당한다.

```bash
$ oc patch hostsubnet <node_name> -p \
'{"egressIPs": ["<IP_address_1>", "<IP_address_2>"]}'
```



- **(권고사항)** 클러스터 관리자는 Namespace 별 사용 가능한 static IPs를 할당한다. 이는 특정 Namespace에서 나가는 트래픽을 쉽게 구분시켜 주는 장점이 있다.  

```bash
$ oc patch netnamespace <project_name> -p '{"egressIPs": ["<IP_address>"]}'
```





### Router 구성 정보

- 소스 IP
- 타겟 IPs
- GW IP



###  Egress Router Mode

-  redirect mode : 
-  HTTP proxy mode
-  DNS proxy mode



