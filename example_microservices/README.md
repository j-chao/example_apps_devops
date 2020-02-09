# Getting Started with Microservices


### Local Environment

Run Postgres database locally with docker.
```sh
$ docker run -p 5432:5432 -e POSTGRES_PASSWORD=password -d postgres:12.1
$ psql -h localhost -U postgres 
``` 

Run migrations for patient microservice
```sh
$ mvn clean flyway:migrate -Dflyway.configFiles=src/main/resources/application.properties
``` 


Consume from Kafka topic with kafkacat
```sh
$ kafkacat -C -b 172.28.33.50:32110 -t demo-topic
```


### Kafka with Strimzi
Create a namespace for the Kafka cluster.
```sh
$ kubectl create ns kafka
```

Deploy the Strimzi operator, and deploy a single-node cluster.
```sh
$ kubectl apply -f strimzi-kafka/strimzi-cluster-operator-0.16.0.yaml
$ kubectl apply -f strimzi-kafka/kafka-persistent-single.yaml -n kafka
```

Demo Kafka Producer
```sh
$ kubectl -n kafka run kafka-producer -ti --image=strimzi/kafka:0.16.0-kafka-2.4.0 --rm=true --restart=Never -- bin/kafka-console-producer.sh --broker-list my-cluster-kafka-bootstrap:9092 --topic my-topic
```

Demo Kafka Consumer
```sh
$ kubectl -n kafka run kafka-consumer -ti --image=strimzi/kafka:0.16.0-kafka-2.4.0 --rm=true --restart=Never -- bin/kafka-console-consumer.sh --bootstrap-server my-cluster-kafka-bootstrap:9092 --topic my-topic --from-beginning
```



