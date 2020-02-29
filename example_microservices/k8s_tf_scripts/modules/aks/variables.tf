variable "resource_group_name" {
  description = "Name of the resource group."
}

variable "prefix" {
  description = "A prefix used for all resources in this example"
}

variable "location" {
  description = "The Azure Region in which all resources in this example should be provisioned"
}

variable "vm_pool_name" {
  description = "Name of the agent pool."
}

variable "vm_count" {
  description = "Number of worker nodes."
}

variable "vm_size" {
  description = "Size of the VMs."
}

variable "kubernetes_client_id" {
  description = "The Client ID for the Service Principal to use for this Managed Kubernetes Cluster"
}

variable "kubernetes_client_secret" {
  description = "The Client Secret for the Service Principal to use for this Managed Kubernetes Cluster"
}

variable "aks_depends_on" {
  description = "Custom variable to hold inter-module dependencies in Terraform."
}
