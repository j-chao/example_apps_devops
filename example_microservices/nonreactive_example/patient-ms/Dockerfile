FROM openjdk:11

COPY ./target/patient-ms-0.0.1-SNAPSHOT.jar /usr/src/patient-ms/
 
WORKDIR /usr/src/patient-ms
EXPOSE 8080

RUN chgrp -R -L root /usr/src/patient-ms && \
  chmod -R g=u+w /usr/src/patient-ms

ENTRYPOINT java -jar patient-ms-0.0.1-SNAPSHOT.jar

