## Deployment vs StatefulSet

StatefulSet은 상태를 유지해야 하는 DB 같은 App을 손쉽게 관리하기 위한 컨트롤러입니다. 특징은 다음과 같습니다.

- StatefulSet으로 생성되는 Pod의 이름은 규칙성을 띕니다. 예를 들어 web이라는 StatefulSet으로 생성되는 Pod는 web-0, web-1, web-2와 같이Pod가 생성됩니다. 또한 StatefulSet으로 생성하면 Pod는 DNS에 액세스할 수 있는 hostname을 제공받을 수 있습니다. 따라서 hostname 기반으로 Pod 간의 클러스터를 맺고 싶을 때, StatefulSet을 사용합니다.
- 배포 시 순차적인 기동과 업데이트가 됩니다. 모든 Pod를 동시에 생성하지 않고, 0,1,2 순서로 하나씩 Pod를 생성합니다. 순차 기동이 필요한 App의 경우에 유용하게 사용됩니다.
- StatefulSet을 활용하면PV를 손쉽게 개별 Pod 별로 각각 붙일 수 있습니다. (기존에 DeploymentConfig 등을 이용할 경우, Pod마다 별도로 PVC, PV 정의를 했어야 함) StatefulSet의 경우 PVC (Persistent Volume Claim)를 volumeClaimTemplate형태로 정의하여, Pod 마다 각각PVC와 PV를 생성하여 관리할 수 있도록 합니다.
  실 사용 사례 중에는, 빅데이터 분석 환경에서 Apache Storm Cluster를 구성하기 위해 StatefulSet으로 구성한 사례, MongoDB, Redis Cluster 등에 구현사례가 있습니다. 핵심은 마스터와 슬레이브 등으로 클러스터가 구성되고, 해당 마스터와 슬레이브에 변하지 않는 고유 정보(hostname)가 필요한 경우 입니다.

> - Deployment : You specify a **PersistentVolumeClaim** that is shared by all pod replicas. In other words, shared volume.  The backing storage obviously must have **ReadWriteMany** or **ReadOnlyMany** accessMode if you have more than one replica pod.
> - StatefulSet : You specify a **volumeClaimTemplates** so that each replica pod gets a unique **PersistentVolumeClaim** associated with it. In other words, no shared volume.
> Here, the backing storage can have **ReadWriteOnce** accessMode.  StatefulSet is useful for running things in cluster e.g Hadoop cluster, MySQL cluster, where each node has its own storage.

  ```yaml
  apiVersion: v1
  kind: Service
  metadata:
    name: nginx
    labels:
      app: nginx
  spec:
    ports:
    - port: 80
      name: web
    clusterIP: None
    selector:
      app: nginx
  ---
  apiVersion: apps/v1
  kind: StatefulSet
  metadata:
    name: web
  spec:
    selector:
      matchLabels:
        app: nginx # has to match .spec.template.metadata.labels
    serviceName: "nginx"
    replicas: 3 # by default is 1
    template:
      metadata:
        labels:
          app: nginx # has to match .spec.selector.matchLabels
      spec:
        terminationGracePeriodSeconds: 10
        containers:
        - name: nginx
          image: k8s.gcr.io/nginx-slim:0.8
          ports:
          - containerPort: 80
            name: web
          volumeMounts:
          - name: www
            mountPath: /usr/share/nginx/html
    volumeClaimTemplates:
    - metadata:
        name: www
      spec:
        accessModes: [ "ReadWriteOnce" ]
        storageClassName: "my-storage-class"
        resources:
          requests:
            storage: 1Gi
  ```

