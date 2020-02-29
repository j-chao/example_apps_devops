resource "azuread_application" "k8s" {
  name = var.sp_name
}

resource "azuread_service_principal" "k8s_sp" {
  application_id = azuread_application.k8s.application_id
}

resource "random_string" "k8s_sp_password" {
  length  = 16
  special = true

  keepers = {
    service_principal = azuread_service_principal.k8s_sp.id
  }
}

resource "azuread_service_principal_password" "k8s_sp_password" {
  service_principal_id = "${azuread_service_principal.k8s_sp.id}"
  value                = "${random_string.k8s_sp_password.result}"
  end_date             = "${timeadd(timestamp(), "8760h")}"

  # This stops be 'end_date' changing on each run and causing a new password to be set
  # to get the date to change here you would have to manually taint this resource...
  lifecycle {
    ignore_changes = [end_date]
  }
}
