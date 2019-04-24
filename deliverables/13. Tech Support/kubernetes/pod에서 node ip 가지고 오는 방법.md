# pod에서 node ip 가지고 오는 방법



- pod 내부에서 Node IP를 환경변수로 가져오는 방법

``` yaml
spec:
  containers:
  - name: micro-svc-1
    env:
    - name: NODE_IP
      valueFrom:
        fieldRef:
          fieldPath: status.hostIP  # Node IP
          #fieldPath: status.podIP  # Pod IP
    - name: POD_NAMESPACE
      valueFrom:
        fieldRef:
          fieldPath: metadata.namespace
    - name: NODE_NAME
      valueFrom:
        fieldRef:
          fieldPath: spec.nodeName
```

