# Pod의 hosts 파일 entry 추가



### 개요

- 사내  DNS  및 네트워크 설정이  안 되어 있는 경우 PodSpec의 HostAliases를 활용하여 Pod의 /etc/hosts 파일 내 필요한 정보를 추가할 수 있다.
- HostAliases 기능이 아닌 /etc/hosts 파일의 수정은 권고하지 않는다.  Kubelet에 의해 Pod의 생성 및 재실행시 관리되고 있기 때문이다.



### 샘플 예제

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: hostaliases-pod
spec:
  restartPolicy: Never
  hostAliases:
  - ip: "127.0.0.1"
    hostnames:
    - "foo.local"
    - "bar.local"
  - ip: "10.1.2.3"
    hostnames:
    - "foo.remote"
    - "bar.remote"
  containers:
  - name: cat-hosts
    image: busybox
    command:
    - cat
    args:
    - "/etc/hosts"
```

```bash
kubectl logs hostaliases-pod

# Kubernetes-managed hosts file.
127.0.0.1	localhost
::1	localhost ip6-localhost ip6-loopback
fe00::0	ip6-localnet
fe00::0	ip6-mcastprefix
fe00::1	ip6-allnodes
fe00::2	ip6-allrouters
10.200.0.5	hostaliases-pod

# Entries added by HostAliases.
127.0.0.1	foo.local	bar.local
10.1.2.3	foo.remote	bar.remote
```

