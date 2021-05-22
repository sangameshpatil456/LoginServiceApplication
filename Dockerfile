# --- Jar file From AWS Ec2 Direct ---

#FROM openjdk:8-jdk-alpine
#MAINTAINER Sangamesh Patil
#COPY login-service.jar login-service.jar
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","/login-service.jar"]

# --- WAR File ---

#From tomcat:8.0.51-jre8-alpine
#CMD ["catalina.sh","run"]

# --- Jar File From Target---

FROM openjdk:8-jdk-alpine
MAINTAINER Sangamesh Patil
COPY target/login-service.jar /login-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/login-service.jar"]

#From tomcat:8.0.51-jre8-alpine
#CMD ["catalina.sh","run"]
