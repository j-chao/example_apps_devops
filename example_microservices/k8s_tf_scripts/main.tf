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
  principal_id         = "${module.k8s-sp.k8s_sp_id}"
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



#module "aks-demo" {
  #source = "modules/aks"

  #k8s_resource_group = "${azurerm_resource_group.k8s.name}"
  #k8s_name = "cumulus-k8s"
  #k8s_subnet_id = "${module.k8s-network.subnet["id"]}"
  #vm_name = "worker-2"
  #vm_location = "eastus2"
  #vm_zone = 3
  #vm_size = "Standard_B2ms"
  #private_ip = "10.240.0.22"
  #lb_controller_address_pool_id = "${module.k8s-network.lb_controller_address_pool_id}"
  #create_controller_pool = 0
#}


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
