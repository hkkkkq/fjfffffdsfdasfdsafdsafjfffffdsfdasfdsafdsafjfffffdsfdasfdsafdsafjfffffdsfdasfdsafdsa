## RBAC in Kubenetes

#### Role-Based Access Control

Can "Bob" "get" "pods"?

Can "subject" "verb" "object"?

즉 누가(subject) 무엇(object)에 대하여 원하는 행위(verb)를 할 수 있느냐에 대하여 Role의 Rule로서 정의하고 이 정의된 Role을 RoleBinding을 통하여 Subject에게 Grant(권한부여) 해주는 관리 방법이다. 



#### RBAC 3-components

- Subjects : User, Group, ServiceAccount
- Rules (sub 2 items)
  - API Resources(Objects)
  - Operations(Verbs)



In a real cluster we may want to have different users, groups and privileges.

Kubernetes provides no API for users.

- Kubernetes doesn't have users. Not really true, but it's easier to understand.
  - ServiceAccount가 k8s에서 관리하는 자격증명이다.

- "Users" are just strings associated with a request through credentials.



API server is pluggable. Users is completely managed outside Kubernetes.

- x509 client certificate
- Password files
- Bearer tokens
- HTTP basic auth
- etc

> ex) Bearer tokens
>
> 
>
> 1. Request
>
> POST /apis/apps/v1/namespaces/ns1/deployments
>
> Authorization: Bearer eyJhbGci0iJSUzI1NiI...
>
> 
>
> 2. Parse request attributes (POST /apis/apps/v1/namespaces/ns1/deployments)
>
> | Verb      | create      |
> | --------- | ----------- |
> | API group | apps        |
> | Namespace | ns1         |
> | Resource  | deployments |
>
> 
>
> 3. **Authenticate subject** (Authorization: Bearer eyJhbGci0iJSUzI1NiI...)
>
> | Username | bob                  |
> | -------- | -------------------- |
> | Group    | system:authenticated |
>
> 
>
> 4. **Authorization**
>
> - 위 2의 request attribute 정보에 대한 권한 확인
>
> 
>
> ex) x509 client certificate
>
> 
>
> /etc/kubenetes/pki/ca.crt
>
> /etc/kubenetes/pki/ca.key
>
> Two important fields in the SSL certificate:
>
> - Common Name (CN): Kubenetes will interpret this value as the **user**
> - Organization (O): Kubenetes will interpret this value as the **group**
>
> 
>
> User 생성
>
> 1. Certificate 생성
> 2. kubectl config set-cluster
> 3. kubectl config set-credentials (위 1의 키와 인증서 활용)
> 4. kubectl config set-context (위 2와 3을 연결 설정)
> 5. kubectl config use-context (User 변경)



#### ServiceAccount 

- Credentials managed by k8s API.

- Only way to create users through k8s API.
  - Credential stored in secrets
    - ServiceAccount를 확인하면 Secret 정보를 확인할 수 있다.
    - Secret 안에는 API server의 ca.crt, signed JWT(Json Web Token)이 있다.
      - 이 토근을 가지고 Bearer Token처럼 Authentication(인증) 시점에 클러스터 외부에서 사용할 수 있다.
      - Service accounts authenticate with the username `system:serviceaccount:(NAMESPACE):(SERVICEACCOUNT)`, and are assigned to the groups `system:serviceaccounts` and `system:serviceaccounts:(NAMESPACE)`.
- Automatically mounted into Pods
  - Gives each pod credentials for talking to the API server.
- This is bound to specific namespaces.



#### User accounts vs service accounts

Kubernetes는 여러가지 이유로 User account와 Service account를 구분한다.

- User account는 사람이고 Service account는 Pods를 실행시키는 프로세스이다.
- User account는 cluster scope이고 Service account는 namespace scope이다.
- User account 생성은 기업의 데이터베이스 및 여러 비지니스와 시스템 연동으로 복잡하지만, 그에 비해 Service account의 생성은 간단한다.



#### Role in RBAC

- a set of allowed operations over a set of namespaced api resources

```yaml
kind: Role
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  namespace: default
  name: pod-access
rules:
- apiGroups: [""] # "" indicates the core API group
  resources: ["pods"]
  verbs: ["get", "watch", "list"]
```



#### RoleBinding in RBAC

- Connects a role to a subject or set of subjects

```yaml
# This role binding allows "jane" to read pods in the "default" namespace.
kind: RoleBinding
apiVersion: rbac.authorization.k8s.io/v1
metadata:
  name: read-pods
  namespace: default
subjects:
- kind: User # Group, ServiceAccount
  name: jane # Name is case sensitive
  apiGroup: rbac.authorization.k8s.io
roleRef:
  kind: Role #this must be Role or ClusterRole
  name: pod-acess # this must match the name of the Role or ClusterRole you wish to bind to
  apiGroup: rbac.authorization.k8s.io
```



#### CluterRole in RBAC

- a set of allowed operations over a set of api resources in the whole cluster
- namespace 정보 없다.
  - But this can apply them cluster wide, or per namespace. So more useful than Roles.



#### ClusterRoleBinding in RBAC

- Connects a clusterrole to a subject or set of subjects
- namespace 정보 없다.



#### Default ClusterRole

- cluster-admin: superuser
- admin, edit, view: namespaced user roles



## 고려사항

- RBAC을 통한 User, ServiceAccount의 권한 관리는 클러스터 내 많은 네임스페이스가 존재하는 환경에서 역할에 대한 정의와 이로 인한 행위의 독립성 보장에 필요하다.
- Kubenetes의 API server는 client와 HTTP rest 통신을 하고 이를 CLI 형식으로 kubectl를 사용하여 통신한다.
  - 지금과 같이 root의 cluster-admin Role을 가지고 kubectl을 사용한다면 불 가능하고 이에 대한 리스크는 존재한다.
  - 다음의 방법을 생각해 볼 수 있다.
    1. 현재 Linux OS 계정 분리 생성을 통하여 각 계정마다 User Account 및 Service Account를 매핑하여 권한 부여 후 API server와 통신한다.
    2. Oath 인증 Layer를 두어 사번에 대한 인증 정보를 얻어 API server와 통신한다.

- Openshift PAAS 제품은 이미 이러한 기능을 가지고 있을 것이다.
  - cli 명령을 통한 low level의 동작 확인을 선호하고
  - 아직 UI에 익숙하지 않은 것 때문에
  - 그리고 정말 cli 통한 기능을 모두 커버 가능한지에 대한 의문이 들어서

- 당장 이슈가 되는 사항은 아니고 이를 섣불리 적용하면 더 큰 혼돈이 있을 수 있다. 그러나 장기적인 관점에서 관리는 해야한다.
  - Red-hat의 국내 및 국외 구축 및 가이드  경험을 참고해 보자.