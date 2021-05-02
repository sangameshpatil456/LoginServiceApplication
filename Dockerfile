FROM openjdk:8-jdk-alpine
MAINTAINER sangamesh Patil
COPY login-service.jar login-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/login-service.jar"]

#From tomcat:8.0.51-jre8-alpine
#CMD ["catalina.sh","run"]
