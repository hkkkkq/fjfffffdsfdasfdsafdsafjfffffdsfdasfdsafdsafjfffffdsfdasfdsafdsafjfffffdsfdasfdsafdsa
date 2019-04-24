# Pod timezone 설정하기

### 개요

컨테이너 클러스터 환경의 기본 Timezone이 UTC로 설정되어 있는 경우가 많다.

```bash
$ date
#Tue Mar 19 13:50:02 KST 2019 (Asia/Seoul 기준 시간)
Tue Mar 19 04:50:02 UTC 2019
```

아래와 같은 방법으로 Pod의 timezone 변경이 가능하다.



### timezone 확인하기

```bash
$ kubectl exec -it deployment-micro-svc-1-5fd94b984b-mg8qx date
Tue Mar 19 04:50:02 UTC 2019
```



### timezone 설정하기

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: deployment-micro-svc-1
spec:
  ...
  template:
    ...
    spec:
      containers:
        ...
        volumeMounts:
        - mountPath: /etc/localtime
          name: timezone-config
      volumes:
      - hostPath:
          path: /usr/share/zoneinfo/Asia/Seoul
        name: timezone-config
```



### java application 내 timezone 설정하기

Base Image내 포함이 되어 있거나 Dockerfile에 명시함으로써 JVM default timezone을 설정해준다.

```bash
ENV TZ=Asia/Seoul
```



 



