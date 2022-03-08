FROM openjdk:13-oracle

ADD build/libs/RewardMicroService-1.0.0.war RewardMicroService.war

EXPOSE 8082

ENTRYPOINT ["java", "-jar","RewardMicroService.war"]