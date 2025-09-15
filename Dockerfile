FROM amazoncorretto:24.0.2-alpine
WORKDIR /krainet_notification_service
COPY target/krainet_notification_service-0.0.1-SNAPSHOT.jar krainet_notification_service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","krainet_notification_service.jar"]