# 클러스터 내 Service 외부 expose 가이드



### 개요

k8s, istio의 컨테이너 클러스터 환경에서 Service를 외부로 노출시켜 외부의 Client가 접속할 수 있는 통로의 역할은  Ingress controller/gateway가 담당한다. Openshift Container Platform에서는 Router를 Infra 노드에 설치하여 외부 사용자에게 동일한 기능을 제공한다. Openshift Route 객체를 활용하면 컨테이너 클러스터 환경으로 들어오는 HTTP(s) INbound 네트워크를 컨트롤 할 수 있다.



### Route

 route.openshift.io/v1 객체를 이용하여 Router 설정을 한다. 다음과 같은 내용으로 구성된다.

- Host Name (Virtual host)
- Service Selector
- Optional security configuration



### Sample Route yaml

```yaml
apiVersion: route.openshift.io/v1
kind: Route
metadata:
  name: route-micro-svc-1
spec:
  host: micro-svc.container.ipc.kt.com
  port:
    #targetPort: 80    # has to be port name!!
    targetPort: http
  path: "/svc1"
  to:
    kind: Service
    name: service-micro-svc-1 #.herasoo.svc.cluster.local  # FQDN is not working
    weight: 100
  wildcardPolicy: None
```



### Router

- Openshift enterprise HAproxy 이미지로 생성된다.
- Host Name Mapping(Virtaul hosting)과 Service로의 요청에 대한 Load Balancing을 제공한다. 외부에서 접속 시 사용하는 DNS 명과 IP 정보 판별은 Route의 역할은 별개이다. 즉 DNS 명과 IP 정보의 발행은  KT DMS(통합도메인관리시스템) http://ktdms.kt.com [도메인신청]을 통해 관리된다.

- Pod로 Infra 노드에서 동작하고 기본적으로 80(HTTP), 443(HTTPS) 포트를 바인딩한다.



### 방화벽 작업

각 클러스터 환경 별로 KTIS망과 DMZ망을 구분하여 Infra 노드가 존재하고 각 노드에 Router가 설치되어 있다. 그리고 상위에 망별로 L4가 연결되어 있다. 외부 Client 위치적 환경요소를 구분하여 해당 L4 IP의 80, 443 포트로 방화벽 작업을 진행하면 된다. 

![20190306185654](assets/20190306185654.jpg)



### 도메인 등록

- host (Virtual host) 정보를 DNS 시스템에 등록하는 작업이 필요하다. 등록할 도메인 정보와 방화벽 작업 시 L4 IP를 기준으로 kate-WORK-BPM-신청업무-도메인통합관리시스템 http://ktdms.kt.com [도메인신청] 등록한다.

- DNS에 등록되기 전이라면 로컬 hosts 파일에 도메인정보 추가하여 테스트하도록 한다.

```bash
10.217.58.25      micro-svc.container.ipc.kt.com 	# DEV환경 DMZ Infra Node L4
10.217.59.30      micro-svc2.container.ipc.kt.com   # DEV환경 KTIS Infra Node L4
```



### 공인인증서 신청

- 등록한 도메인 정보에 맞추어 공인인증서 신청은 kate-WORK-BPM-신청업무-도메인통합관리시스템 http://ktdms.kt.com [SSL관리] 에서 가능하다.

- TEST 및 특정 환경에 국한하여 자기서명 인증서를 생성하여 테스트 가능하다.



### 서버 인증서 설치 위치

- 공인인증서의 경우 host 기반으로 가격 정책이 이루어진다.
- L4에 인증서 설치 가능하며, 뒷 단 웹서버, Ingress Controller 및 Router에도 설정이 가능하다.
- L4 설치의 경우 인증서 개수가 줄어 인증서 구입 비용이 절약될 수 있으나 인증서 유효기간에 따른 갱신 관리 및 관리주체의 차이로 devops 사상과는 거리가 멀다.

- Ingress Controller 및 Router에 설정하는 것을 권고한다.



### Router tls 설정

Route의 tls 설정을 통해 HTTPs 전송구간 암호화 설정하는 방법을 설명하고자 한다.

- 개인 키 및 자기서명 인증서 생성

유료로 인증된 CA로부터 공인 인증서를 구입하는 것이 Best일 수 있겠지만 상황에 따라 자기가 서명한 사설 인증서 사용이 불 가피하다. 프로젝트 DEV환경이 그 중 하나의 예시 상황이 될 수 있다.

```bash
# 1. 개인키 생성
openssl genrsa -des3 -out micro-svc.key 1024
=> ex. 패스워드 입력: 123456

# 2. 패스워드 삭제 (현재 oc HAproxy는 password protected key files are not supported!!!!)
openssl rsa -in micro-svc.key -out micro-svc.key

# 3. 개인키 보기
openssl rsa -noout -text -in micro-svc.key

# 4. 인증서 생성
openssl req -new -x509 -sha1 -key micro-svc.key -out micro-svc.crt -days 7300
=> C=KR, ST=SEOUL, L=SEOUL, O=KT, OU=KT, CN=micro-svc.container.ipc.kt.com

# 5. 인증서 보기
openssl x509 -noout -text -in micro-svc.crt
```



- 공인인증서

공인 인증서를 구매하면 다음과 같은 파일이 존재한다.

```bash
-rwxr--r--. 1 root root 1988 Mar 11 19:22 ChainCA1.crt
-rwxr--r--. 1 root root 2210 Mar 11 19:22 ChainCA2.crt
-rwxr--r--. 1 root root 5744 Mar 11 19:22 Chain_RootCA_Bundle.crt
-rwxr--r--. 1 root root 1546 Mar 11 19:22 RootCA.crt
-rwxr--r--. 1 root root 2548 Mar 11 19:22 test_insuretech_kt_com_cert.pem
-rwxr--r--. 1 root root 1679 Mar 11 19:25 test_insuretech_kt_com_key.pem

# 패스워드 삭제 (현재 oc HAproxy는 password protected key files are not supported!!!!)
openssl rsa -in test_insuretech_kt_com_key.pem -out test_insuretech_kt_com_key.pem
```



- Sample Route yaml (개인 키 및 자기 서명 인증서 사용)

```yaml
apiVersion: route.openshift.io/v1
kind: Route
metadata:
  name: route-micro-svc-1
spec:
  host: micro-svc.container.ipc.kt.com
  port:
    #targetPort: 80    # has to be port name!!
    targetPort: http
  path: "/svc1"
  to:
    kind: Service
    name: service-micro-svc-1 #.herasoo.svc.cluster.local  # FQDN is not working
    weight: 100
  wildcardPolicy: None
  tls:
    certificate: |
      -----BEGIN CERTIFICATE-----
      MIICrjCCAhegAwIBAgIJAKudO7SvriyGMA0GCSqGSIb3DQEBBQUAMHAxCzAJBgNV
      BAYTAktSMQ4wDAYDVQQIDAVTRU9VTDEOMAwGA1UEBwwFU0VPVUwxCzAJBgNVBAoM
      AktUMQswCQYDVQQLDAJLVDEnMCUGA1UEAwwebWljcm8tc3ZjLmNvbnRhaW5lci5p
      cGMua3QuY29tMB4XDTE5MDMwNjA1MTYzNFoXDTM5MDMwMTA1MTYzNFowcDELMAkG
      A1UEBhMCS1IxDjAMBgNVBAgMBVNFT1VMMQ4wDAYDVQQHDAVTRU9VTDELMAkGA1UE
      CgwCS1QxCzAJBgNVBAsMAktUMScwJQYDVQQDDB5taWNyby1zdmMuY29udGFpbmVy
      LmlwYy5rdC5jb20wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAKkZ63WhIPPW
      WGuBhmrwQuGMqxuH7e2I4LIYXrEBHvt36KogtiC1BcChgH+PbtSEDi9xe5NGHWQS
      79XI+PqRNpzso/Kv7Mmke1zO5ReMrH74WwN8BS90s7PGg1lcoR1hpmPkKnDQJNnl
      +IAGsFnmRfZ+6uIS79CsPmDgB/tzJlmRAgMBAAGjUDBOMB0GA1UdDgQWBBRdeo/B
      I9ULouCh1Yi9soj+4kTxezAfBgNVHSMEGDAWgBRdeo/BI9ULouCh1Yi9soj+4kTx
      ezAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBBQUAA4GBAGHs8CnMPuZzJhccHW39
      9QfKwXb3TgXFCahIkFu40Fu0alhAmbl5Lkipkwyeq7Y7esfHsfdqWhI2k6OM3Tg4
      d5LL+NDjGO/05ZXsNKHq50mlaN/gW7xIbKPHHrdddSauFFDRkEWmIKSdHMTY3FvE
      wPgTsCd8mXazs7PT1SbDUV97
      -----END CERTIFICATE-----
    insecureEdgeTerminationPolicy: Redirect # None, Allow
    key: |
      -----BEGIN RSA PRIVATE KEY-----
      MIICXgIBAAKBgQCpGet1oSDz1lhrgYZq8ELhjKsbh+3tiOCyGF6xAR77d+iqILYg
      tQXAoYB/j27UhA4vcXuTRh1kEu/VyPj6kTac7KPyr+zJpHtczuUXjKx++FsDfAUv
      dLOzxoNZXKEdYaZj5Cpw0CTZ5fiABrBZ5kX2furiEu/QrD5g4Af7cyZZkQIDAQAB
      AoGBAJT1u0Akj0Wc5MjXEZS0TwYnBze6VvIu6FiaNRfRd/xnFXKAXE+Vhxq4adx0
      eloEdgZ+/i+uzQcx00nMvt7i+mp0/a5OPnHlt9UnGoCRTACBT41I5iZD0op1CkgB
      llBD2yhOGRJuzxY0WIY4kqqp36eU1yb7F/z2qQnvtlPDeaQdAkEA2NZM0x9w4aFT
      Kg/J1o8M5WMokzxVvDiF44k5DoSlVG90derA2v9f5r+kc+uEAy71ArL3bNGXW3a2
      OVhM/AdUOwJBAMekgArNBJiljHm6dHkXXfH1ZDMTbmVdSTaHyTlIFyRy4fZeXulN
      6dWCYHrAvdUsZkoRJvfo8wGC5n1Nu7dPqKMCQHlY1H2CJuXeJnBvjYDpZUoqB9b9
      +OzwpN0FOk0JpoxuIzqqOWtVZxH3Rc0pjCNL8Ammphjb4lxpPukwpUSVxWkCQQCp
      m0JAas6V1EcUf2hGG6jvTzdyAPcP3IG/dvpn1Kv8hjEb+i6N7GhUwG+wuaBMw2yf
      ThUaFmikhw3DtE9eu6kbAkEAlH/8PAfz9+DA4GOoWk/09D7F2YCAYhzhGehXzS4j
      H2iesBQZjE2VqDlgHyHiTSVVHFwKeeK/7HYvthr0qi2kjg==
      -----END RSA PRIVATE KEY-----
    termination: edge
```

1. 개인키와 인증서 파일의 정보를 가지고 yaml로 설정할 수 있다.

2. openshift web-console UI를 통해 개인키와 인증서 파일을 선택 및 drag&drop으로 설정할 수 있다.

3. spec.insecureEdgeTerminationPolicy

   1) None: HTTP 허용하지 않는다.

   2) Allow: HTTP 허용한다.

   3) Redirect: Redirect 302 status로 리턴한다.

4. spec.termination

   1) edge: Route가 tls 종단점이 되어 이후 HTTP로 내부에 전달된다.

   2) passthrough: Route에 tls 관련 설정이 존재하지 않으며 네트워크를 그대로 통과시킨다.

   3) re-encryption: Route까지 tls 전송구간을 종단점으로 이후 새로운 키와 인증서로 전송구간을 생성한다.



- Sample Route yaml (공인인증서 사용)

```yaml
apiVersion: route.openshift.io/v1
kind: Route
metadata:
  name: route-micro-svc-1
spec:
  host: test.insuretech.kt.com
  path: /svc1
  port:
    targetPort: http
  tls:
    caCertificate: |-		# Chain_RootCA_Bundle.crt
      -----BEGIN CERTIFICATE-----
      MIIGGTCCBAGgAwIBAgIQE31TnKp8MamkM3AZaIR6jTANBgkqhkiG9w0BAQwFADCB
      iDELMAkGA1UEBhMCVVMxEzARBgNVBAgTCk5ldyBKZXJzZXkxFDASBgNVBAcTC0pl
      cnNleSBDaXR5MR4wHAYDVQQKExVUaGUgVVNFUlRSVVNUIE5ldHdvcmsxLjAsBgNV
      BAMTJVVTRVJUcnVzdCBSU0EgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkwHhcNMTgx
      MTAyMDAwMDAwWhcNMzAxMjMxMjM1OTU5WjCBlTELMAkGA1UEBhMCR0IxGzAZBgNV
      BAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4GA1UEBxMHU2FsZm9yZDEYMBYGA1UE
      ChMPU2VjdGlnbyBMaW1pdGVkMT0wOwYDVQQDEzRTZWN0aWdvIFJTQSBPcmdhbml6
      YXRpb24gVmFsaWRhdGlvbiBTZWN1cmUgU2VydmVyIENBMIIBIjANBgkqhkiG9w0B
      AQEFAAOCAQ8AMIIBCgKCAQEAnJMCRkVKUkiS/FeN+S3qU76zLNXYqKXsW2kDwB0Q
      9lkz3v4HSKjojHpnSvH1jcM3ZtAykffEnQRgxLVK4oOLp64m1F06XvjRFnG7ir1x
      on3IzqJgJLBSoDpFUd54k2xiYPHkVpy3O/c8Vdjf1XoxfDV/ElFw4Sy+BKzL+k/h
      fGVqwECn2XylY4QZ4ffK76q06Fha2ZnjJt+OErK43DOyNtoUHZZYQkBuCyKFHFEi
      rsTIBkVtkuZntxkj5Ng2a4XQf8dS48+wdQHgibSov4o2TqPgbOuEQc6lL0giE5dQ
      YkUeCaXMn2xXcEAG2yDoG9bzk4unMp63RBUJ16/9fAEc2wIDAQABo4IBbjCCAWow
      HwYDVR0jBBgwFoAUU3m/WqorSs9UgOHYm8Cd8rIDZsswHQYDVR0OBBYEFBfZ1iUn
      Z/kxwklD2TA2RIxsqU/rMA4GA1UdDwEB/wQEAwIBhjASBgNVHRMBAf8ECDAGAQH/
      AgEAMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjAbBgNVHSAEFDASMAYG
      BFUdIAAwCAYGZ4EMAQICMFAGA1UdHwRJMEcwRaBDoEGGP2h0dHA6Ly9jcmwudXNl
      cnRydXN0LmNvbS9VU0VSVHJ1c3RSU0FDZXJ0aWZpY2F0aW9uQXV0aG9yaXR5LmNy
      bDB2BggrBgEFBQcBAQRqMGgwPwYIKwYBBQUHMAKGM2h0dHA6Ly9jcnQudXNlcnRy
      dXN0LmNvbS9VU0VSVHJ1c3RSU0FBZGRUcnVzdENBLmNydDAlBggrBgEFBQcwAYYZ
      aHR0cDovL29jc3AudXNlcnRydXN0LmNvbTANBgkqhkiG9w0BAQwFAAOCAgEAThNA
      lsnD5m5bwOO69Bfhrgkfyb/LDCUW8nNTs3Yat6tIBtbNAHwgRUNFbBZaGxNh10m6
      pAKkrOjOzi3JKnSj3N6uq9BoNviRrzwB93fVC8+Xq+uH5xWo+jBaYXEgscBDxLmP
      bYox6xU2JPti1Qucj+lmveZhUZeTth2HvbC1bP6mESkGYTQxMD0gJ3NR0N6Fg9N3
      OSBGltqnxloWJ4Wyz04PToxcvr44APhL+XJ71PJ616IphdAEutNCLFGIUi7RPSRn
      R+xVzBv0yjTqJsHe3cQhifa6ezIejpZehEU4z4CqN2mLYBd0FUiRnG3wTqN3yhsc
      SPr5z0noX0+FCuKPkBurcEya67emP7SsXaRfz+bYipaQ908mgWB2XQ8kd5GzKjGf
      FlqyXYwcKapInI5v03hAcNt37N3j0VcFcC3mSZiIBYRiBXBWdoY5TtMibx3+bfEO
      s2LEPMvAhblhHrrhFYBZlAyuBbuMf1a+HNJav5fyakywxnB2sJCNwQs2uRHY1ihc
      6k/+JLcYCpsM0MF8XPtpvcyiTcaQvKZN8rG61ppnW5YCUtCC+cQKXA0o4D/I+pWV
      idWkvklsQLI+qGu41SWyxP7x09fn1txDAXYw+zuLXfdKiXyaNb78yvBXAfCNP6CH
      MntHWpdLgtJmwsQt6j8k9Kf5qLnjatkYYaA7jBU=
      -----END CERTIFICATE-----
      -----BEGIN CERTIFICATE-----
      MIIFdzCCBF+gAwIBAgIQE+oocFv07O0MNmMJgGFDNjANBgkqhkiG9w0BAQwFADBv
      MQswCQYDVQQGEwJTRTEUMBIGA1UEChMLQWRkVHJ1c3QgQUIxJjAkBgNVBAsTHUFk
      ZFRydXN0IEV4dGVybmFsIFRUUCBOZXR3b3JrMSIwIAYDVQQDExlBZGRUcnVzdCBF
      eHRlcm5hbCBDQSBSb290MB4XDTAwMDUzMDEwNDgzOFoXDTIwMDUzMDEwNDgzOFow
      gYgxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpOZXcgSmVyc2V5MRQwEgYDVQQHEwtK
      ZXJzZXkgQ2l0eTEeMBwGA1UEChMVVGhlIFVTRVJUUlVTVCBOZXR3b3JrMS4wLAYD
      VQQDEyVVU0VSVHJ1c3QgUlNBIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MIICIjAN
      BgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAgBJlFzYOw9sIs9CsVw127c0n00yt
      UINh4qogTQktZAnczomfzD2p7PbPwdzx07HWezcoEStH2jnGvDoZtF+mvX2do2NC
      tnbyqTsrkfjib9DsFiCQCT7i6HTJGLSR1GJk23+jBvGIGGqQIjy8/hPwhxR79uQf
      jtTkUcYRZ0YIUcuGFFQ/vDP+fmyc/xadGL1RjjWmp2bIcmfbIWax1Jt4A8BQOujM
      8Ny8nkz+rwWWNR9XWrf/zvk9tyy29lTdyOcSOk2uTIq3XJq0tyA9yn8iNK5+O2hm
      AUTnAU5GU5szYPeUvlM3kHND8zLDU+/bqv50TmnHa4xgk97Exwzf4TKuzJM7UXiV
      Z4vuPVb+DNBpDxsP8yUmazNt925H+nND5X4OpWaxKXwyhGNVicQNwZNUMBkTrNN9
      N6frXTpsNVzbQdcS2qlJC9/YgIoJk2KOtWbPJYjNhLixP6Q5D9kCnusSTJV882sF
      qV4Wg8y4Z+LoE53MW4LTTLPtW//e5XOsIzstAL81VXQJSdhJWBp/kjbmUZIO8yZ9
      HE0XvMnsQybQv0FfQKlERPSZ51eHnlAfV1SoPv10Yy+xUGUJ5lhCLkMaTLTwJUdZ
      +gQek9QmRkpQgbLevni3/GcV4clXhB4PY9bpYrrWX1Uu6lzGKAgEJTm4Diup8kyX
      HAc/DVL17e8vgg8CAwEAAaOB9DCB8TAfBgNVHSMEGDAWgBStvZh6NLQm9/rEJlTv
      A73gJMtUGjAdBgNVHQ4EFgQUU3m/WqorSs9UgOHYm8Cd8rIDZsswDgYDVR0PAQH/
      BAQDAgGGMA8GA1UdEwEB/wQFMAMBAf8wEQYDVR0gBAowCDAGBgRVHSAAMEQGA1Ud
      HwQ9MDswOaA3oDWGM2h0dHA6Ly9jcmwudXNlcnRydXN0LmNvbS9BZGRUcnVzdEV4
      dGVybmFsQ0FSb290LmNybDA1BggrBgEFBQcBAQQpMCcwJQYIKwYBBQUHMAGGGWh0
      dHA6Ly9vY3NwLnVzZXJ0cnVzdC5jb20wDQYJKoZIhvcNAQEMBQADggEBAJNl9jeD
      lQ9ew4IcH9Z35zyKwKoJ8OkLJvHgwmp1ocd5yblSYMgpEg7wrQPWCcR23+WmgZWn
      RtqCV6mVksW2jwMibDN3wXsyF24HzloUQToFJBv2FAY7qCUkDrvMKnXduXBBP3zQ
      YzYhBx9G/2CkkeFnvN4ffhkUyWNnkepnB2u0j4vAbkN9w6GAbLIevFOFfdyQoaS8
      Le9Gclc1Bb+7RrtubTeZtv8jkpHGbkD4jylW6l/VXxRTrPBPYer3IsynVgviuDQf
      Jtl7GQVoP7o81DgGotPmjw7jtHFtQELFhLRAlSv0ZaBIefYdgWOWnU914Ph85I6p
      0fKtirOMxyHNwu8=
      -----END CERTIFICATE-----
      -----BEGIN CERTIFICATE-----
      MIIENjCCAx6gAwIBAgIBATANBgkqhkiG9w0BAQUFADBvMQswCQYDVQQGEwJTRTEU
      MBIGA1UEChMLQWRkVHJ1c3QgQUIxJjAkBgNVBAsTHUFkZFRydXN0IEV4dGVybmFs
      IFRUUCBOZXR3b3JrMSIwIAYDVQQDExlBZGRUcnVzdCBFeHRlcm5hbCBDQSBSb290
      MB4XDTAwMDUzMDEwNDgzOFoXDTIwMDUzMDEwNDgzOFowbzELMAkGA1UEBhMCU0Ux
      FDASBgNVBAoTC0FkZFRydXN0IEFCMSYwJAYDVQQLEx1BZGRUcnVzdCBFeHRlcm5h
      bCBUVFAgTmV0d29yazEiMCAGA1UEAxMZQWRkVHJ1c3QgRXh0ZXJuYWwgQ0EgUm9v
      dDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALf3GjPm8gAELTngTlvt
      H7xsD821+iO2zt6bETOXpClMfZOfvUq8k+0DGuOPz+VtUFrWlymUWoCwSXrbLpX9
      uMq/NzgtHj6RQa1wVsfwTz/oMp50ysiQVOnGXw94nZpAPA6sYapeFI+eh6FqUNzX
      mk6vBbOmcZSccbNQYArHE504B4YCqOmoaSYYkKtMsE8jqzpPhNjfzp/haW+710LX
      a0Tkx63ubUFfclpxCDezeWWkWaCUN/cALw3CknLa0Dhy2xSoRcRdKn23tNbE7qzN
      E0S3ySvdQwAl+mG5aWpYIxG3pzOPVnVZ9c0p10a3CitlttNCbxWyuHv77+ldU9U0
      WicCAwEAAaOB3DCB2TAdBgNVHQ4EFgQUrb2YejS0Jvf6xCZU7wO94CTLVBowCwYD
      VR0PBAQDAgEGMA8GA1UdEwEB/wQFMAMBAf8wgZkGA1UdIwSBkTCBjoAUrb2YejS0
      Jvf6xCZU7wO94CTLVBqhc6RxMG8xCzAJBgNVBAYTAlNFMRQwEgYDVQQKEwtBZGRU
      cnVzdCBBQjEmMCQGA1UECxMdQWRkVHJ1c3QgRXh0ZXJuYWwgVFRQIE5ldHdvcmsx
      IjAgBgNVBAMTGUFkZFRydXN0IEV4dGVybmFsIENBIFJvb3SCAQEwDQYJKoZIhvcN
      AQEFBQADggEBALCb4IUlwtYj4g+WBpKdQZic2YR5gdkeWxQHIzZlj7DYd7usQWxH
      YINRsPkyPef89iYTx4AWpb9a/IfPeHmJIZriTAcKhjW88t5RxNKWt9x+Tu5w/Rw5
      6wwCURQtjr0W4MHfRnXnJK3s9EK0hZNwEGe6nQY1ShjTK3rMUUKhemPR5ruhxSvC
      Nr4TDea9Y355e6cJDUCrat2PisP29owaQgVR1EX1n6diIWgVIEM8med8vSTYqZEX
      c4g/VhsxOBi0cQ+azcgOno4uG+GMmIPLHzHxREzGBHNJdmAPx/i9F4BrLunMTA5a
      mnkPIAou1Z5jJh5VkpTYghdae9C8x49OhgQ=
      -----END CERTIFICATE-----
    certificate: |-			# test_insuretech_kt_com_cert.pem
      -----BEGIN CERTIFICATE-----
      MIIHLjCCBhagAwIBAgIQOO7kgEA1TXra8OnlU3YfLTANBgkqhkiG9w0BAQsFADCB
      lTELMAkGA1UEBhMCR0IxGzAZBgNVBAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G
      A1UEBxMHU2FsZm9yZDEYMBYGA1UEChMPU2VjdGlnbyBMaW1pdGVkMT0wOwYDVQQD
      EzRTZWN0aWdvIFJTQSBPcmdhbml6YXRpb24gVmFsaWRhdGlvbiBTZWN1cmUgU2Vy
      dmVyIENBMB4XDTE5MDMxMDAwMDAwMFoXDTIwMDQwMTIzNTk1OVowggENMQswCQYD
      VQQGEwJLUjEOMAwGA1UEERMFMTM2MDYxFDASBgNVBAgTC0d5ZW9uZ2dpLWRvMSAw
      HgYDVQQHExdCdW5kYW5nLWd1LCBTZW9uZ25hbS1zaTEYMBYGA1UECRMPOTAsIEJ1
      bGplb25nLXJvMRcwFQYDVQQKEw5LVCBDb3Jwb3JhdGlvbjELMAkGA1UECxMCSVQx
      QDA+BgNVBAsTN0hvc3RlZCBieSBLb3JlYSBJbmZvcm1hdGlvbiBDZXJ0aWZpY2F0
      ZSBBdXRob3JpdHksIEluYy4xEzARBgNVBAsTCkluc3RhbnRTU0wxHzAdBgNVBAMT
      FnRlc3QuaW5zdXJldGVjaC5rdC5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAw
      ggEKAoIBAQCGsKBlHZ+57GV5jWqcaBEUFJl4th2c3NUSU8BDvYkf+JwqN3SkVbH8
      jpAoWtBgQV5saahdAVhMvScmE5v7bvpovqJOrM8WvTljQ8fWlZTStMP4HzkEzLZL
      LwucDOG9qhaK6Li/+/xWuONjzfsfScaNfAhjJXyzU6LENHUdQV98c9MDeNmKy2cE
      PtSutEDxQao/OhMHbZK3i8rZld3MUClvkU+h6S9RGm4TH3O0IHm5wEtWBX6VRetw
      aoDFDNAQOtC0Qf0KpbKg6CGbcNpu/85byPdaPEfOnC0hVKWZlNcWvHpQ92Mn3h6y
      PHXUFviFS3HpEsGkehGv9joaa+b2uasHAgMBAAGjggL9MIIC+TAfBgNVHSMEGDAW
      gBQX2dYlJ2f5McJJQ9kwNkSMbKlP6zAdBgNVHQ4EFgQUxk40cA2vmrlDnQnuOqlY
      PXm0pYUwDgYDVR0PAQH/BAQDAgWgMAwGA1UdEwEB/wQCMAAwHQYDVR0lBBYwFAYI
      KwYBBQUHAwEGCCsGAQUFBwMCMEoGA1UdIARDMEEwNQYMKwYBBAGyMQECAQMEMCUw
      IwYIKwYBBQUHAgEWF2h0dHBzOi8vc2VjdGlnby5jb20vQ1BTMAgGBmeBDAECAjBa
      BgNVHR8EUzBRME+gTaBLhklodHRwOi8vY3JsLnNlY3RpZ28uY29tL1NlY3RpZ29S
      U0FPcmdhbml6YXRpb25WYWxpZGF0aW9uU2VjdXJlU2VydmVyQ0EuY3JsMIGKBggr
      BgEFBQcBAQR+MHwwVQYIKwYBBQUHMAKGSWh0dHA6Ly9jcnQuc2VjdGlnby5jb20v
      U2VjdGlnb1JTQU9yZ2FuaXphdGlvblZhbGlkYXRpb25TZWN1cmVTZXJ2ZXJDQS5j
      cnQwIwYIKwYBBQUHMAGGF2h0dHA6Ly9vY3NwLnNlY3RpZ28uY29tMD0GA1UdEQQ2
      MDSCFnRlc3QuaW5zdXJldGVjaC5rdC5jb22CGnd3dy50ZXN0Lmluc3VyZXRlY2gu
      a3QuY29tMIIBBAYKKwYBBAHWeQIEAgSB9QSB8gDwAHYAu9nfvB+KcbWTlCOXqpJ7
      RzhXlQqrUugakJZkNo4e0YUAAAFpaeA6RAAABAMARzBFAiAv7SEVwGrylBqWdmGg
      Fxk1jJI/g6f9gB6BVwEMJcshYwIhAN3lnEI+bisT8M59U1Bvzb2TBPWlG4ZsFz7E
      pOkjwo7GAHYAXqdz+d9WwOe1Nkh90EngMnqRmgyEoRIShBh1loFxRVgAAAFpaeA6
      eAAABAMARzBFAiEA/mE24EZkdqJDBw+7GJ11CBLMstrmofPko+yEGx+/B/ACIC6S
      4t4nARgP+W+Uf2n+R/IjSwnUr3/j9OokEDZ7CYdwMA0GCSqGSIb3DQEBCwUAA4IB
      AQBpgNJjXgbPw/j/sEAf4JUfRCR8vwWsPee/B9cTHQlnrfSs9KS2LbyFQhUovG3q
      DeRpo9CHCSH0A2ybc1IKsZdrz6QLkT/s7KtAfuTeAHZc9xRAd4msRGgjxUhEaqZ/
      GCwpgwbOpo58ayjyOVGm/nhhh+bX9KWBKCZLS69yS0wukDsoOdpiJ6O6iTp2yaY1
      EPWxmp3TBo9KBP9qtlSOjMNZ9OG38J8GzHEt44AUt/WTtpwKivDlXwkEY+pF5uGw
      VNsmE8pLfpvMziQmhvFB7D5xOJwRRU95uBCj7tfnCJ10jXsLlMQvmbY5+5VLSVs0
      uRT+Xqz+4ibdSteU61G3jyrt
      -----END CERTIFICATE-----
    key: |-				# test_insuretech_kt_com_key.pem
      -----BEGIN RSA PRIVATE KEY-----
      MIIEpAIBAAKCAQEAhrCgZR2fuexleY1qnGgRFBSZeLYdnNzVElPAQ72JH/icKjd0
      pFWx/I6QKFrQYEFebGmoXQFYTL0nJhOb+276aL6iTqzPFr05Y0PH1pWU0rTD+B85
      BMy2Sy8LnAzhvaoWiui4v/v8VrjjY837H0nGjXwIYyV8s1OixDR1HUFffHPTA3jZ
      istnBD7UrrRA8UGqPzoTB22St4vK2ZXdzFApb5FPoekvURpuEx9ztCB5ucBLVgV+
      lUXrcGqAxQzQEDrQtEH9CqWyoOghm3Dabv/OW8j3WjxHzpwtIVSlmZTXFrx6UPdj
      J94esjx11Bb4hUtx6RLBpHoRr/Y6Gmvm9rmrBwIDAQABAoIBACcUQkV0Ve609zBQ
      ZrlgvGvOGkVa2ba0oCHEKQm+iXW8RVs+Ydp4fr7fglYjG0iCTuo3scDibgM98ojq
      sZw/M+qGPUOEgkV/u5Hgja/mEJseFan9LScGWnHwAXdSiYDlQ8skAM67RxjGZjuD
      /TOzzScp8KkLrGW/g39YFQ7a8+CAsrlYg1zLOhOq+kFKQwYcHb2gSu/lbkXANllG
      3p0VQbDDKSOGRoPdQK9GdKBpO3MVpa6jtfhix/5epRbSntncIdZQ9ql/cl/i43k+
      EmJHs6KjBQQ5HogdZTqIhN7GUrCfCNPkPZhHWdKIESCEpHG6kJzWPJU4nvW60rgB
      gWl+tNECgYEAw1dqQ5Bij1C5qYg6EEHKep0ZpcJxdEzdfsIBBS+RNYXTqvY8WZy9
      v7DSKET6x3aAGRV9Ru8lmwqB3VUziQkKDyfkZUSP/+mkr+94YChjG4ZrU5ZZUuQD
      Xr3LwW5L2+qL0bEUc0LDZNF7wmcPIlTnyvBbp4Kxhxq5IjDkeMd6/+8CgYEAsIO+
      0jigur5e5OV/ZWpKXpwAIoUhn+bl3xM+Tp3aBD5Ux+TqGsgWqnj8PCRVv3PFM3JJ
      SGi88CmBP9ucZuOAB9U3hQbobG8qTbyypllg4iNZM5noK5LAXpexqlrSDkOUQJi1
      sdYzl0r9vzmhu74Z7IedOG5fN6jGxidEf/uSbmkCgYEAm5nKP7+Hgvmeqvikp8aA
      b2Msxuk4Tpfb59jJz0Owu526mfhC4m8EhulmvkUULkaqi1Ce1h4xKmsYPyJbRtE4
      fhIc0L3opsWKNcrfbFk/ePF+FLsys+CE/rbY01/0dV1Duuj/jUClPG3R020clRBl
      AzarC6gDmccjqaSgmcTsqWkCgYAMRr1oxYN4OXFEHioEFaZiehsQS44RHu/x2dbF
      hPj6vpMK7ALUJgJdjNiOHkDQde7LKzGplY8+bMSOemmgRkZLRSr9V4OmtvyMN4l9
      RE4WrDqx+gxJq4Uxjv9OR87AKzcOXxCT87/Wz+JiAFIdSpVm+++VUyfQKS7Z7TU4
      aXi/sQKBgQCaD8X429NVKcgXmxLgUOWHIh/XAecKzUNqXFcaicbAKb33uZo4SO8u
      xOhzrdKZ7USQvFh2Pu/592hRevpyxmQ0Qvim6+0+SbHZdmydNVtb+3ol2ZLggJNx
      IohfEwqalSH2gPMNSX4UmQWr6/eXJvJ5rbwQwrqencJ7WCph1v+/lw==
      -----END RSA PRIVATE KEY-----
    insecureEdgeTerminationPolicy: Redirect # None, Allow
    termination: edge
  to:
    kind: Service
    name: service-micro-svc-1
    weight: 100
  wildcardPolicy: None
```

개인키, 인증서와 더불어 chain+root ca 인증서도 함께 설정하여 준다.



### Virtual host 등록 및 Routing 정책 설정



#### Kubernetes



#### Istio



### 정적(static) 파일 제공 서비스

.html, 이미지 파일, .css, font파일, .js, 특정 배포 파일과 같은 리소스에 대하여 기존 웹 서버에 위치 시켜 놓았던 방법과는 달리 별도의 웹 서버 이미지를 활용하여 Pod로 띄워 서비스를 제공하고 특정 /path로 Routing하여 사용한다. 순전히 Ingress Controller/Router는 Virtual hosting과 Routing만 수행하도록 가이드 한다. 