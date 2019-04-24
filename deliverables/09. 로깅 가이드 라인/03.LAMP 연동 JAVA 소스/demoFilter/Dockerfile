FROM ktis-bastion01.container.ipc.kt.com:5000/admin/openjdk-millet:8-jdk-alpine

ENV TZ Asia/Seoul
COPY ./target/demo-0.0.1-SNAPSHOT.jar lampFilter.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/lampFilter.jar"]
