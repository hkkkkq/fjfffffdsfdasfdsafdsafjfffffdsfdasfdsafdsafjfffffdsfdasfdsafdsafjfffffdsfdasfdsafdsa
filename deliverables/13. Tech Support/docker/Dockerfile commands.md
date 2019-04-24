## Dockerfile commands

소문자 사용도 가능하지만 일반적으로 대문자를 사용한다.

#### 1. FROM

```dockerfile
# 베이스 이미지
FROM ktis-bastion01.container.ipc.kt.com:5000/admin/openjdk:8-jre-alpine
# 깡통 이미지
FROM scratch => 깡통이미지
```

#### 2. LABEL

```dockerfile
# 이미지 메타데이터에 추가, docker inspect 통해 확인
LABEL com.example.version="0.0.1-beta"
LABEL vendor="kt ds Incorporated"
LABEL author="herasoo"
LABEL com.example.release-date="2015-02-12"
```

#### 3. RUN

```dockerfile
# 이미지를 만들기 위해 컨테이너 내부에서 명령어 실행
# 빌드 시 입력을 받아야 하는 RUN이 있다면 오류로 간주하고 종료된다.
# JSON 배열 형태 가능 ["sh","-c","echo $MY_ENV"]
RUN apt-get update
RUN apt-get install apache2 -y
# RUN 명령어만큼 이미지 Layer가 생성되기 때문에 가능하면 아래와 같이 줄이는 것을 권장한다.
RUN apt-get update && apt-get install apache2 -y
```

#### 4. COPY (recommended)

```dockerfile
# 빌드 Context-path의 파일을 이미지에 추가
# JSON 배열 형태 가능 ["test.html",...,"/var/www/html"]
COPY test.html /var/www/html
```

#### 5. ADD (not recommended)

```dockerfile
# 빌드 Context-path의 파일을 이미지에 추가 + 외부URL 및 tar 파일에서도 파일 추가 가능
# tar의 경우 tar를 그대로 추가하는 것이 아니라 tar 파일을 자동으로 해제해서 추가한다.
# 이미지에 정확히 어떤 파일이 추가된 것인지 알 수 없기에 ADD 사용은 권장하지 않는다.
# JSON 배열 형태 가능 ["test.html",...,"/var/www/html"]
ADD test.html /var/www/html
```

#### 6. WORKDIR

```dockerfile
# cd명령과 동일한 역할을 하나 RUN cd ... 의 경우 해당 RUN 명령어에서만 유효하다.
WORKDIR /var/www/html
```

#### 7. ENV

```dockerfile
# 환경변수
# Dockerfile 내 뿐만 아니라 이미지에도 저장되어 컨테이너에서도 사용할 수 있다.
# ${test}, $test 와 같이 사용
ENV test /home
WORKDIR $test
```

#### 8. VOLUME (not recommended)

```dockerfile
# 컨테이너 내부 Dir를 Host와 공유한다.
# JSON 배열 형태 가능 ["컨테이너 디렉터리 1", "컨테이너 디렉터리2"]
# VOLUME으로는 호스트의 특정 디렉터리와 연결할 수는 없습니다. host의 /var/lib/docker/volumes/{volume_name}으로 저장된다.
VOLUME /home/volume
# docker run -v /root/data:/home/volume example
# docker run -v 사용을 권장하고 DockerFile 내 VOLUME보다 우선한다.
```

#### 9. ARG
```bash
docker build --build-arg my_arg1=value1 -t myarg:0.1 .
```
```dockerfile
# docker build 시 parameters를 전달받아 사용
# 기본값을 지정하여 ENV와 같이 사용도 가능
FROM ubuntu:14.04
ARG my_arg1
ARG my_arg2=value2
RUN touch ${my_arg1}_${my_arg2}.txt
```
#### 10. USER

```dockerfile
# 컨테이너에서 사용될 계정 설정한다.
# 일반적으로 RUN을 통해 계정을 생성하여 사용하며 루트 권한이 필요하지 않다면 사용을 권장한다.
RUN groupadd -r king && useradd -r -g king herasoo
USER herasoo
```

#### 11. EXPOSE

```dockerfile
# 이미지 노출 포트 설정
# 컨테이너의 노출 포트에 대한 정보전달이지 실제 포트를 기동하는 것은 아니다.
EXPOSE 80
EXPOSE 8080
```

#### 12. CMD

```dockerfile
# 컨테이너 시작 시 실행 명령어 설정으로 Dockerfile 내 한번만 설정
# docker run 시 입력된 명령어로 덮어쓰여진다.
# JSON 배열 형태 가능 ["apachectl", "-DFOREGROUND"] 하며 권장사항이다.
CMD ["apachectl", "-DFOREGROUND"]
```

#### 13. ENTRYPOINT

```dockerfile
# ENTRYPOINT vs CMD
# CMD와 같이 컨테이너 실행 역할을 하나 차이점이 존재한다.
# ENTRYPOINT가 설정되어 있다면 CMD는 단지 매개변수의 기능을 한다.
# CMD, ENTRYPOINT 경우 JSON 형태로 명령어를 입력하면 입력된 명령어가 그대로 이미지에 사용되나, 일반형식으로 사용될 경우 /bin/sh -c 가 앞에 추가된다. JSON 형태 사용을 권장한다.
 ex) ENTRYPOINT: 없음, CMD: /bin/bash => /bin/bash 가 실행
     ENTRYPOINT: echo, CMD: /bin/bash => echo /bin/bash 가 실행
```

#### 14. Multi stage build

```dockerfile
# 2개 이상의 FROM을 통해 이미지를 생성.
# 일반적으로 APP 빌드(컴파일) 시 의존성 패키지와 라이르러리로 인해 최종 이미지 파일이 커지는 경우, 1번째 이미지에서 빌드하고 그 결과물을 2번째로 COPY하는 방식으로 최종 이미지 크기를 줄일 수 있다.

FROM golang as builder
ADD main.go /root
WORKDIR /root
RUN go build -o /root/mainApp /root/main.go

FROM alpine:latest
WORKDIR /root
COPY --from=builder /root/MainApp .
CMD ["./mainApp"]
```

