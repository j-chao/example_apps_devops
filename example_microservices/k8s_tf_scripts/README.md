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



## Kubespray

### Configure SSH connectivity to bastion host and VMs
TODO: automate this step from Terraform with dynamic inventory, and with Kubespray

Generate SSH keys for connecting to the bastion host from your local host, and other VMs from the bastion host.
Copy the kubespray directory to the k8s-bastion host for provisioning the Kubernetes cluster via Ansible.


Install Python in the bastion host.
```bash
$ sudo yum install https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
$ sudo yum install python-pip
```

Install the necessary Python libraries for Kubespray.
```bash
$ sudo pip install -r kubespray/requirements.txt
```

Check that the bastion host is able to successfully connect to all necessary VMs for provisioning.
```bash
$ ansible all -m ping -i inventory/cumulus-k8s/hosts.ini
```

### Configure and execute Kubespray
Check that the cloud configurations for Azure are configured correctly in the kubespray/inventory/cumulus-k8s/group_vars/all/azure.yml file.

Check external load balancer IP address for 'cumulus-k8s-pip' is defined correctly in the kubespray/inventory/cumulus-k8s/group_vars/all/all.yml file.

Set download_container to false.

Finally, execute the ansible playbook.
```bash
$ ansible-playbook -i inventory/cumulus-k8s/hosts.ini -b cluster.yml
```




### Creating Azure Service Principal
az ad sp create-for-rbac --role=Contributor --scopes=/subscriptions/a9bd9e52-304c-484b-aee7-5de8991bed82


### Accessing K8s UI
az aks get-credentials --resource-group cumulus-infrastructure --name cumulus-k8s
az aks browse --resource-group cumulus-infrastructure --name cumulus-k8s

az aks get-credentials --resource-group jchao-infrastructure --name jchao-k8s
az aks browse --resource-group jchao-infrastructure --name jchao-k8s


# need to eventually move to using service account
kubectl create clusterrolebinding kubernetes-dashboard --clusterrole=cluster-admin --serviceaccount=kube-system:kubernetes-dashboard

kubectl proxy

http://localhost:8001/api/v1/namespaces/kube-system/services/kubernetes-dashboard/proxy




### Configuring firewalld for RHEL and CentOS systems

Run on master nodes:
```bash
firewall-cmd --permanent --add-port=6443/tcp
firewall-cmd --permanent --add-port=2379/tcp
firewall-cmd --permanent --add-port=2380/tcp
firewall-cmd --permanent --add-port=10250/tcp
firewall-cmd --permanent --add-port=10251/tcp
firewall-cmd --permanent --add-port=10252/tcp
firewall-cmd --permanent --add-port=10255/tcp
firewall-cmd —reload
```

Run on all nodes:
```bash
firewall-cmd --permanent --add-port=30000-32767/tcp
firewall-cmd --permanent --add-port=10250/tcp
firewall-cmd --permanent --add-port=10255/tcp
firewall-cmd --permanent --add-port=6783/tcp
```
