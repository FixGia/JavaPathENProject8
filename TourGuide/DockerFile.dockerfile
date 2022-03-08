FROM openjdk:13-oracle
EXPOSE 9090
ADD build/libs/TourGuide-1.0.0.war TourGuide.war
ENTRYPOINT ["java","-jar","TourGuide.war"]