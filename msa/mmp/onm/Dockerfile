FROM ktis-bastion01.container.ipc.kt.com:5000/admin/openjdk-millet:8-jdk-alpine

LABEL owner=mmp-project-team
ENV TZ Asia/Seoul
COPY target/*.jar app.jar
ENV JAVA_OPTS="-Xms1G -Xmx1G -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m"
ENV JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:+G1SummarizeConcMark -XX:InitiatingHeapOccupancyPercent=35 -XX:G1ConcRefinementThreads=20"
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]