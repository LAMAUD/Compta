FROM maven:3.5.0-jdk-8 as builder
WORKDIR /usr/src/mymaven
COPY pom.xml /usr/src/mymaven
COPY src /usr/src/mymaven/src
COPY compta-core /usr/src/mymaven/compta-core
COPY compta-web /usr/src/mymaven/compta-web
COPY compta-security-config /usr/src/mymaven/compta-security-config
COPY elastic-apm-agent-1.14.0.jar /usr/src/mymaven/elastic-apm-agent-1.14.0.jar
RUN mvn -f /usr/src/mymaven/pom.xml clean package -DskipTests


FROM openjdk:8u131-jre-alpine
WORKDIR /root/
COPY --from=builder /usr/src/mymaven/elastic-apm-agent-1.14.0.jar /root/
COPY --from=builder /usr/src/mymaven/compta-web/target/compta-web-0.0.1-SNAPSHOT.jar /root/
COPY --from=builder /usr/src/mymaven/compta-web/src/main/resources/config.json /root/
EXPOSE 80 8080
	
CMD java -javaagent:elastic-apm-agent-1.14.0.jar \
     -Delastic.apm.service_name=compta \
     -Delastic.apm.server_urls=http://localhost:8200 \
     -Delastic.apm.secret_token= \
     -Delastic.apm.application_packages=com.clamaud.compta \
     -jar compta-web-0.0.1-SNAPSHOT.jar

