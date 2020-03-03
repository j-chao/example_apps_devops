# DevOps Workshop

This repository holds example applications that can be used for understanding DevOps:

- containerization with Docker
- container orchestration with Kubernetes & OpenShift
- CI/CD Pipelines with Jenkins

## EFK Stack

Deploy ElasticSearch, Fluentd, and Kibana on Kubernetes:
```bash
$ helm install efk efk_stack/
```

Depending on the environment, there are different configurations for how to deploy the EFK stack.
Change the "environment variable" in values.yaml accordingly, to your needs.

### Local Environment parameters
Elasticsearch
- single node Deployment

Fluentd
- deployment kind


### Azure Environment
Elasticsearch
- 3-node StatefulSet 

Fluentd
- daemonset kind



Kubernetes Cluster: v1.13.12

