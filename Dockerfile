FROM maven:3.5.0-jdk-8 as builder
WORKDIR /usr/src/mymaven
COPY pom.xml /usr/src/mymaven
COPY src /usr/src/mymaven/src
COPY compta-core /usr/src/mymaven/compta-core
COPY compta-web /usr/src/mymaven/compta-web
COPY compta-security-config /usr/src/mymaven/compta-security-config
RUN mvn -f /usr/src/mymaven/pom.xml clean package -DskipTests

FROM openjdk:8u131-jre-alpine
WORKDIR /root/
COPY --from=builder /usr/src/mymaven/compta-web/target/compta-web-0.0.1-SNAPSHOT.jar /root/
EXPOSE 80 8080
CMD java -jar compta-web-0.0.1-SNAPSHOT.jar
