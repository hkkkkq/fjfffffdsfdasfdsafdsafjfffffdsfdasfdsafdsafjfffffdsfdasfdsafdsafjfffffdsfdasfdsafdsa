# Kubernetes Autoscaling



### 개요

Horizontal Pod Autoscaler는 CPU 사용량(또는 베타 지원의 다른 애플리케이션 지원 메트릭)을 관찰하여 rc, deployment, rs와 같이 Pod 크기 조정이 가능한 오브젝트에 대하여 Pod 개수를 자동으로 스케일한다.

kube-controller-manager 설정(**--horizontal-pod-autoscaler-sync-period** 플래그)에 의해 15초 주기를 가지고 정의에 지정된 메트릭에 대해 리소스 사용률을 질의한다. 

CPU에 대한 오토스케일링 지원만 포함하는 안정된 버전은 `autoscaling/v1` API 버전에서 찾을 수 있다. 메모리 및 사용자 정의 메트릭에 대한 스케일링 지원을 포함하는 베타 버전은 `autoscaling/v2beta2`에서 확인할 수 있다. 



### 설정 방법 1

```bash
$ kubectl autoscale deployment php-apache --cpu-percent=50 --min=1 --max=10
```
위와 같이  cpu 사용률 50%를 유지하기 위해  replica를 1~10까지 auto scaling하도록 설정한다.



### 설정 방법 2

동일하게 yaml 파일로 관리할 수 있다.

```yaml
apiVersion: autoscaling/v1
kind: HorizontalPodAutoscaler
metadata:
  name: php-apache
  namespace: default
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: php-apache
  minReplicas: 1
  maxReplicas: 10
  targetCPUUtilizationPercentage: 50
```



### 동작 예제



```bash
$ kubectl autoscale deployment php-apache --cpu-percent=50 --min=1 --max=10
horizontalpodautoscaler.autoscaling/php-apache autoscaled
```

```shell
$ kubectl get hpa
NAME         REFERENCE                     TARGET    MINPODS   MAXPODS   REPLICAS   AGE
php-apache   Deployment/php-apache/scale   0% / 50%  1         10        1          18s
```

이후 부하를 발생하면,

```bash
$ kubectl get hpa
NAME         REFERENCE                     TARGET      CURRENT   MINPODS   MAXPODS   REPLICAS   AGE
php-apache   Deployment/php-apache/scale   305% / 50%  305%      1         10        1          3m
```

`CURRENT`은 디플로이먼트에 의해 제어되는 파드들의 평균을 나타낸다.

```shell
$ kubectl get deployment php-apache
NAME         DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
php-apache   7         7         7            7           19m
```


