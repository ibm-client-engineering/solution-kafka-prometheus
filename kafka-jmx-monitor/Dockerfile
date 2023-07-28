# Start with a base image containing Java runtime
FROM openjdk:8-jdk

# Add Maintainer Info
LABEL maintainer="chris.watkins@ibm.com"

# Make port 9999 available to the world outside this container
EXPOSE 8080

# The application's base directory
WORKDIR /opt/kafka-jmx-monitor

# The application's jar file
ARG JAR_FILE=target/kafka-jmx-monitor-1.0-jar-with-dependencies.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar


ADD ${JAR_FILE} app.jar

#COPY ./target/dependency/ /opt/kafka-jmx-monitor


# Run the jar file 
ENTRYPOINT ["java","-jar","app.jar"]