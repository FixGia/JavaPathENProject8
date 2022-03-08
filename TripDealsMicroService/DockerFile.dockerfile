FROM openjdk:13-oracle

ADD build/libs/TripDealMicroService-1.0.0.war TripDealMicroService.war

EXPOSE 8083

ENTRYPOINT ["java", "-jar","TripDealMicroService.war"]