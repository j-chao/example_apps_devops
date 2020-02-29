terraform {
  backend "local" {}
}

provider "azurerm" {
  version = "=1.30.0"
  subscription_id = "67386586-96cb-4acb-8b51-24968af13467"
}

provider "azuread" {
  version = "=0.7.0"
}

provider "random" {
  version = "=2.2.1"
}


data "azurerm_subscription" "oht-aes-nonprod" {}
data "azurerm_client_config" "oht-aes-nonprod" {}

resource "azurerm_resource_group" "demo" {
  name     = "cumulus-demo"
  location = "eastus2"

  tags = {
    environment = "demo"
  }
}

##################################
#### SETUP SERVICE PRINCIPAL #####
##################################

module "k8s-sp" { 
  source = "./modules/service_principal"
  sp_name = "demo"
}

resource "azurerm_role_assignment" "k8s-sp" {
  scope                = "${data.azurerm_subscription.oht-aes-nonprod.id}/resourceGroups/${azurerm_resource_group.demo.name}"
  role_definition_name = "Contributor"
  principal_id         = "${module.k8s-sp.k8s_sp_appid}"
}

######################
######### AKS ########
######################

module "aks" {
  source  = "Azure/aks/azurerm"
  version = "3.0.0"
  resource_group_name = "${azurerm_resource_group.demo.name}"
  client_id           = "${module.k8s-sp.k8s_sp_appid}"
  client_secret       = "${module.k8s-sp.k8s_sp_password}"
  prefix              = "${azurerm_resource_group.demo.name}"
}


######################
####### OUTPUT #######
######################

output "k8s-sp-name" {
  value = "${module.k8s-sp.k8s_sp_name}"
}

output "k8s-sp-appid" {
  value = "${module.k8s-sp.k8s_sp_appid}"
}

output "k8s-sp-password" {
  value = "${module.k8s-sp.k8s_sp_password}"
}

output "k8s-host" {
  value = "${module.aks.host}"
}

output "k8s-aks-id" {
  value = "module.aks.aks_id"
} 
output "k8s-client-certificate" {
  value = "${module.aks.client_certificate}"
}

output "k8s-client-key" {
  value = "${module.aks.client_key}"
}

output "k8s-cluster-ca-certificate" {
  value = "${module.aks.cluster_ca_certificate}"
}

output "k8s-username" {
  value = "${module.aks.username}"
}

output "k8s-password" {
  value = "${module.aks.password}"
}
