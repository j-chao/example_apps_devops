terraform {
  backend "local" {}
}

provider "azurerm" {
  version = "=2.0.0"
  subscription_id = "67386586-96cb-4acb-8b51-24968af13467"
  features {}
}

provider "azuread" {
  version = "=0.7.0"
}

provider "random" {
  version = "=2.2.1"
}

variable "tags" {
  type = map
  default = {
    environment = "demo"
  }
}


data "azurerm_subscription" "oht-aes-nonprod" {}
data "azurerm_client_config" "oht-aes-nonprod" {}

resource "azurerm_resource_group" "demo" {
  name     = "cumulus-demo"
  location = "eastus2"

  tags = var.tags
}

##################################
#### SETUP SERVICE PRINCIPAL #####
##################################

module "k8s-sp" { 
  source = "./modules/service_principal"
  sp_name = azurerm_resource_group.demo.name
}

resource "azurerm_role_assignment" "k8s-sp" {
  scope                = "${data.azurerm_subscription.oht-aes-nonprod.id}/resourceGroups/${azurerm_resource_group.demo.name}"
  role_definition_name = "Contributor"
  principal_id         = module.k8s-sp.id
}

######################
##### SETUP AKS ######
######################

module "aks" {
  source                   = "./modules/aks"
  resource_group_name      = azurerm_resource_group.demo.name
  prefix                   = azurerm_resource_group.demo.name
  location                 = azurerm_resource_group.demo.location
  vm_pool_name             = "default"
  vm_count                 = "2"
  vm_size                  = "Standard_F2"
  kubernetes_client_id     = module.k8s-sp.app_id
  kubernetes_client_secret = module.k8s-sp.password

  aks_depends_on = [module.k8s-sp.password]
}


######################
####### OUTPUT #######
######################

output "k8s_fqdn" {
  value = module.aks.fqdn
}

output "k8s_client_certificate" {
  value = module.aks.kube_config.0.client_certificate
}
