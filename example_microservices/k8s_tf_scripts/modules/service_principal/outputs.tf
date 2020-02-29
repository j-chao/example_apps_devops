output "id" {
  value = "${azuread_service_principal.demo.id}"
}

output "name" {
  value = "${azuread_service_principal.demo.display_name}"
}

output "app_id" {
  value = "${azuread_application.demo.application_id}"
}

output "password" {
  value = "${random_password.demo.result}"
}
