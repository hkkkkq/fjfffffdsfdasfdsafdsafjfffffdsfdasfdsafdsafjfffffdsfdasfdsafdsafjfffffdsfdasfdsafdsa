# << openshift node selector >>



### 개정이력

| 날짜       | 변경내용  | 작성자 | 비고 |
| ---------- | --------- | ------ | ---- |
| 2019.02.22 | 최초 작성 | 송양종 |      |
|            |           |        |      |







pod 를 실행될때 특정 node에 배치시키기 위해서 여러가지 전략이 존재한다.



### 1) cluster-wide default node selector

ㅇ openshift master-config file 위치
/etc/origin/master/master-config.yaml

```bash
...
projectConfig:
  defaultNodeSelector: node-role.kubernetes.io/node=true
...
```

모든 파드는 node=true 인 node 로 배치 된다.




### 2) namespace 설정

특정 네임스페이스에서 실행되는 파드를 특정 node 로 명시 할 수 있다.

네임스페이스내 annotation 으로 node-selector 를 명시하는 방법을 사용한다.


```bash
$ oc describe ns istio-system
Name:         istio-system
Labels:       istio.openshift.com/ignore-namespace=ignore
Annotations:  openshift.io/node-selector=node-role.kubernetes.io/infra=true
              openshift.io/sa.scc.mcs=s0:c16,c10
              openshift.io/sa.scc.supplemental-groups=1000260000/10000
              openshift.io/sa.scc.uid-range=1000260000/10000
Status:       Active
```

istio-system namespace 에서 실행되는 pod는 모두 infra=true 인 node 로 배치된다.

테스트결과 네임스페이스의 설정값이 cluster-wide default 설정값보다 우선한다.

즉, 네임스페이에 설정된 node selector와 defualt 설정값이 틀린경우 네임스페이스 값으로 설정된다.



### 3) manifest 파일에 설정

마지막 세번째로 manifest 파일에서 nodeSelector 를 설정하여 특정 node 로 배치시킬수 있다.

```yaml
---
apiVersion: extensions/v1beta1
kind: DaemonSet
metadata:
  name: filebeat
  # namespace: kube-system
  labels:
    app: filebeat
spec:
  template:
    metadata:
      labels:
        app: filebeat
    spec:
      #serviceAccountName: filebeat
      #terminationGracePeriodSeconds: 30
      nodeSelector:
        node-role.kubernetes.io/node: "true"
        logging: "true"
      #  app: frontend-webserver
      containers:
      - name: filebeat
        image: ktis-bastion01.container.ipc.kt.com:5000/elastic/filebeat:v1_2019022211
        #securityContext:
        #  runAsUser: 0
        #  # If using Red Hat OpenShift uncomment this:
        #  #privileged: true
        volumeMounts:
        - name: testlog
          mountPath: /usr/song
          # readOnly: true
          # subPath: filebeat.yml
      volumes:
      - name: testlog
        hostPath:
          path: /usr/song
          type: Directory
          # type: DirectoryOrCreate
```


하지만 manifest 방법은 상위(1번,2번)와 and 조건으로 실행된다.





### 참고 : ipc  cluster node

```

[bsscoe@ktis-master01 test3_k8s_daemonset]$ oc get nodes
NAME                                 STATUS    ROLES       AGE       VERSION
dmz-infra01.container.ipc.kt.com     Ready     dmz-infra   66d       v1.11.0+d4cacc0
dmz-infra02.container.ipc.kt.com     Ready     dmz-infra   66d       v1.11.0+d4cacc0
dmz-node01.container.ipc.kt.com      Ready     node        66d       v1.11.0+d4cacc0
ktis-infra01.container.ipc.kt.com    Ready     infra       66d       v1.11.0+d4cacc0
ktis-infra02.container.ipc.kt.com    Ready     infra       66d       v1.11.0+d4cacc0
ktis-master01.container.ipc.kt.com   Ready     master      66d       v1.11.0+d4cacc0
ktis-master02.container.ipc.kt.com   Ready     master      66d       v1.11.0+d4cacc0
ktis-master03.container.ipc.kt.com   Ready     master      66d       v1.11.0+d4cacc0
ktis-node01.container.ipc.kt.com     Ready     node        66d       v1.11.0+d4cacc0

oc describe node dmz-infra01.container.ipc.kt.com  
oc describe node dmz-infra02.container.ipc.kt.com  
oc describe node dmz-node01.container.ipc.kt.com   
oc describe node ktis-infra01.container.ipc.kt.com 
oc describe node ktis-infra02.container.ipc.kt.com 
oc describe node ktis-master01.container.ipc.kt.com
oc describe node ktis-master02.container.ipc.kt.com
oc describe node ktis-master03.container.ipc.kt.com
oc describe node ktis-node01.container.ipc.kt.com  

Name:               dmz-infra01.container.ipc.kt.com
Roles:              dmz-infra
Labels:             beta.kubernetes.io/arch=amd64
                    beta.kubernetes.io/os=linux
                    kubernetes.io/hostname=dmz-infra01.container.ipc.kt.com
                    logging=true
                    node-role.kubernetes.io/dmz-infra=true
                    router=true
                    runtime=docker

Name:               dmz-infra02.container.ipc.kt.com
Roles:              dmz-infra
Labels:             beta.kubernetes.io/arch=amd64
                    beta.kubernetes.io/os=linux
                    kubernetes.io/hostname=dmz-infra02.container.ipc.kt.com
                    logging=true
                    node-role.kubernetes.io/dmz-infra=true
                    router=true
                    runtime=docker

Name:               dmz-node01.container.ipc.kt.com
Roles:              node
Labels:             beta.kubernetes.io/arch=amd64
                    beta.kubernetes.io/os=linux
                    dmz-node=true
                    kubernetes.io/hostname=dmz-node01.container.ipc.kt.com
                    logging=true
                    node-role.kubernetes.io/node=true
                    runtime=docker

Name:               ktis-infra01.container.ipc.kt.com
Roles:              infra
Labels:             beta.kubernetes.io/arch=amd64
                    beta.kubernetes.io/os=linux
                    kubernetes.io/hostname=ktis-infra01.container.ipc.kt.com
                    logging=true
                    node-role.kubernetes.io/infra=true
                    router=true
                    runtime=docker

Name:               ktis-infra02.container.ipc.kt.com
Roles:              infra
Labels:             beta.kubernetes.io/arch=amd64
                    beta.kubernetes.io/os=linux
                    kubernetes.io/hostname=ktis-infra02.container.ipc.kt.com
                    logging=true
                    node-role.kubernetes.io/infra=true
                    router=true
                    runtime=docker

Name:               ktis-master01.container.ipc.kt.com
Roles:              master
Labels:             beta.kubernetes.io/arch=amd64
                    beta.kubernetes.io/os=linux
                    kubernetes.io/hostname=ktis-master01.container.ipc.kt.com
                    logging=true
                    node-role.kubernetes.io/master=true
                    runtime=docker

Name:               ktis-master02.container.ipc.kt.com
Roles:              master
Labels:             beta.kubernetes.io/arch=amd64
                    beta.kubernetes.io/os=linux
                    kubernetes.io/hostname=ktis-master02.container.ipc.kt.com
                    logging=true
                    node-role.kubernetes.io/master=true
                    runtime=docker

Name:               ktis-master03.container.ipc.kt.com
Roles:              master
Labels:             beta.kubernetes.io/arch=amd64
                    beta.kubernetes.io/os=linux
                    kubernetes.io/hostname=ktis-master03.container.ipc.kt.com
                    logging=true
                    node-role.kubernetes.io/master=true
                    runtime=docker

Name:               ktis-node01.container.ipc.kt.com
Roles:              node
Labels:             beta.kubernetes.io/arch=amd64
                    beta.kubernetes.io/os=linux
                    ktis-node=true
                    kubernetes.io/hostname=ktis-node01.container.ipc.kt.com
                    logging=true
                    node-role.kubernetes.io/node=true
                    runtime=docker

```

