output "k8s_sp_id" {
  value = "${azuread_service_principal.k8s_sp.id}"
}

output "k8s_sp_name" {
  value = "${azuread_service_principal.k8s_sp.display_name}"
}

output "k8s_sp_appid" {
  value = "${azuread_application.k8s.application_id}"
}

output "k8s_sp_password" {
  value = "${random_string.k8s_sp_password.result}"
}
