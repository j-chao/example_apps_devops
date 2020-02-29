resource "azuread_application" "demo" {
  name = var.sp_name
  homepage                   = "http://demo"
  identifier_uris            = ["http://uri_demo"]
  reply_urls                 = ["http://replyurl_demo"]
  available_to_other_tenants = false
  oauth2_allow_implicit_flow = false
}

resource "azuread_service_principal" "demo" {
  application_id = azuread_application.demo.application_id
  app_role_assignment_required = false
}

resource "random_password" "demo" {
  length = 16
  special = true
  override_special = "_%@"
  keepers = {
    service_principal = azuread_service_principal.demo.id
  }
}

resource "azuread_service_principal_password" "demo" {
  service_principal_id = azuread_service_principal.demo.id
  value                = random_password.demo.result
  end_date_relative    = "720h"

  # This stops be 'end_date' changing on each run and causing a new password to be set
  # to get the date to change here you would have to manually taint this resource...

  lifecycle {
    ignore_changes = [end_date_relative]
  }

}
