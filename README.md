![TourGuidePré](https://user-images.githubusercontent.com/79265943/157535100-a1429eeb-c172-4cab-9df9-02ac3eb6d7d5.png)

# Technology and Prerequisites
* JAVA 1.8 JDk
* Springboot
* Gradle
* Docker

# Application Architectural

TourGuide is composed of 4 microServices :
* TourGuide
* Gps-MicroService
* RewardMicroService
* TripDealMicroService

# Basic Architecture
![ArchiBaseP8 drawio](https://user-images.githubusercontent.com/79265943/158581056-ff39b636-9c72-432a-81ed-f9538bb1ab9c.png)


![ArchiTourGuide drawio](https://user-images.githubusercontent.com/79265943/158581100-23e4ad45-9ced-4643-9424-0d714f3da43f.png)


![ArchiGpsMS (1)](https://user-images.githubusercontent.com/79265943/158581116-5e9d3f50-eca0-46b4-aaff-8be486df92c7.png)


![ArchiRewardMS1](https://user-images.githubusercontent.com/79265943/158581148-d90aad51-7fbf-4b23-860d-428a5429fd75.png)


![TripDealMS drawio](https://user-images.githubusercontent.com/79265943/158581164-5b2f0ed1-fab6-434b-8765-54223f9f3ce8.png)



# Run Config

:one: Gradle
----
* gradle bootRun or ./gradle bootRun
* gradle bootWar or ./gradle bootWar or ./gradle bootJar

2️⃣ Docker
----
To deploy all TourGuide Application, use the docker-compose.yml on the root package.

* Case 1 : SYNTAX = docker-compose up -d
* Case 2 : Open docker-compose.yml and ▶️ the docker-compose.

# Test Report
![rapport jacoco GpsMicroService](https://user-images.githubusercontent.com/79265943/158609523-51b968a9-90f4-46df-ae09-bff9080394c7.png)
![rapport test GPSMS](https://user-images.githubusercontent.com/79265943/158609997-c88f804c-d719-4608-8957-679d19a52745.png)
![rapport jacoco RewardMicroService](https://user-images.githubusercontent.com/79265943/158624232-5875c416-eb3f-4457-bb17-1f687d2c092a.png)
![rapport test RMS](https://user-images.githubusercontent.com/79265943/158624261-047c406f-7806-4c46-adc3-a8d98da8c7b0.png)
![rapport jacoco TripDealMS](https://user-images.githubusercontent.com/79265943/158624296-5482265d-030a-41ab-872a-d38b90ad0e6e.png)
![rapport test TripDealMS](https://user-images.githubusercontent.com/79265943/158624320-0c42011f-0222-4735-9086-33acdfaad7e7.png)
![rapport jacoco TourGuide](https://user-images.githubusercontent.com/79265943/158624355-54f0834b-55df-44ed-8d35-695722956c9e.png)
![rapport Test TourGuide](https://user-images.githubusercontent.com/79265943/158624374-8178ba0a-e62b-444a-8a7c-510366d21419.png)



# Documentation API

* TourGuide http://localhost:8080/swagger-ui.html#/
* GpsMicroService http://localhost:8081/swagger-ui.html#/
