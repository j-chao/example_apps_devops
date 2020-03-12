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

