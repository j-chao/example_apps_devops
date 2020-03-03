# Azure Infrastructure for Team Cumulus

Infrastructure as Code for Team Cumulus.

Provisions Azure infrastructure with Terraform.  
Installs Kubernetes with Kubespray via Ansible.  



# Prerequisites
What things you need to install the software and how to install them

- [Terraform](https://www.terraform.io/downloads.html)

- **Azure account**: This Terraform repository provisions a production-ready Kubernetes cluster on Azure.
Therefore, an Azure account with access to be able to create resources under an Azure subscription account is required.


## Running Terraform locally

Login to you Azure account, and set your working Azure subscription if you have multiple.

```bash
$ az login
$ az account set --subscription <subscription-id>
```

Download any required terraform modules.

```bash
$ terraform init
```

Prior to executing the infrastruction provisioning, it is good to do a test run to check what the script will actually
do.

```bash
$ terraform plan
```

If you are satisfied with the output of what the script will do upon execution, then proceed with executing the
provisioning of the infrastructure.
You can pass the `-auto-approve` flag to skip the interactive approval step, if you are aconfident in the script.

```bash
$ terraform apply -auto-approve
```


Destroy all infrastructure components defined.
```bash
$ terraform destroy
```

```bash
$ terraform plan -out=vpc.plan -target=module.vpc
```


## Get credentials to the AKS cluster

```bash
$ az aks get-credentials --resource-group cumulus-demo --name cumulus-demo-k8s
```


