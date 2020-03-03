variable "aks_depends_on" {
  type    = any
  default = null
}

variable "tags" {
  type = map
  default = {
    environment = "demo"
  }
}

resource "azurerm_kubernetes_cluster" "demo" {
  name                = "${var.prefix}-k8s"
  location            = var.location
  resource_group_name = var.resource_group_name
  dns_prefix          = "${var.prefix}-k8s"
  kubernetes_version  = "1.13.12"

  default_node_pool {
    name       = var.vm_pool_name
    node_count = var.vm_count
    vm_size    = var.vm_size
  }

  service_principal {
    client_id     = var.kubernetes_client_id
    client_secret = var.kubernetes_client_secret
  }

  tags = var.tags
  depends_on = [var.aks_depends_on]
}
