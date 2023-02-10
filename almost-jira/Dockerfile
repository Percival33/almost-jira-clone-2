FROM gradle:7.6.0-jdk17-focal AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle assemble

FROM eclipse-temurin:17.0.6_10-jre-focal

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/almost-jira-0.0.1-SNAPSHOT.jar /app/spring-boot-application.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-application.jar"]