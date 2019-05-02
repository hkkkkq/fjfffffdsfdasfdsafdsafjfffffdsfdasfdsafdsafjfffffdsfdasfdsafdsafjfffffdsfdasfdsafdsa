FROM ktis-bastion01.container.ipc.kt.com:5000/admin/openjdk-millet:8-jdk-alpine

LABEL owner=mmp-project-team
ENV TZ Asia/Seoul

# ahtn ssl client certificate import
RUN mkdir -p /java.io/keystores
COPY src/main/resources/cert/kmcert1.cer /java.io/keystores
RUN keytool -import -alias kmcTrust -file /java.io/keystores/kmcert1.cer -keystore /java.io/keystores/truststore.jks -storepass mmpProject -noprompt

COPY target/*.jar app.jar
ENV JAVA_OPTS="-Xms1G -Xmx1G -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m"
ENV JAVA_OPTS="${JAVA_OPTS} -XX:+UseG1GC -XX:+UnlockDiagnosticVMOptions -XX:+G1SummarizeConcMark -XX:InitiatingHeapOccupancyPercent=35 -XX:G1ConcRefinementThreads=20"

# ahtn ssl client certificate ENV
ENV JAVA_OPTS="${JAVA_OPTS} -Djavax.net.ssl.trustStore=/java.io/keystores/truststore.jks -Djavax.net.ssl.trustStorePassword=mmpProject"

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar"]