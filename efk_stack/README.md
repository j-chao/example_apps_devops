# Elastic Stack


## Getting Started

### Defining Passwords

Define the necessary passwords for the Elastic Stack's built-in users in a .env file, 
located in the same directory as this README, as structured below:

```
# password used by Elasticsearch to bootstrap itself
ES\_BOOTSTRAP\_PASSWORD=<some_password>

# password used by Kibana to conenct to Elasticsearch
ES\_KIBANA\_PASSWORD=<some_password>
```

### Deploying the Elastic Stack on Kubernetes
Use the Makefile to generate the necessary secrets and deploy the Elastic Stack:
```sh
$ make install
```


## Authors
- Justin Chao


## Acknowledgments

#### TODO
- setup metricbeat monitoring for elastic cluster
- setup filebeat monitoring for elastic cluster
- make multiple certificates for each node
  - change certificate verification strategy for elasticsearch nodes to full to check for DNS
# setup native realm for users credentials via RESTful call to es cluster from kibana initcontainer
# configure list of eligible masters
- setup hot-warm-cold architecture
- check compatibility with OSFI k8s
  - convert from using mmapfs to niofs


