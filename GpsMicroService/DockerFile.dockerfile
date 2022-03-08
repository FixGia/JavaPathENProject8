FROM openjdk:13-oracle

ADD build/libs/GpsMicroService-1.0.0.war GpsMicroService.war

EXPOSE 8081

ENTRYPOINT ["java", "-jar","GpsMicroService.war"]