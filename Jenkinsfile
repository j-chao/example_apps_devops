#!/usr/bin/env groovy 
pipeline {

  agent any

  stages {
      
    stage("Git Checkout") {
      steps {
        checkout([$class: 'GitSCM', branches: [[name: 'master']],
        userRemoteConfigs: [[url: 'https://github.com/j-chao/example_apps_devops']]])
      }
    }

    stage("Run Unit Tests") {
      steps {
        sh '''
          python3 -m unittest flask_app/app_test.py -v
          '''
      }
    }

    stage("Build Docker Image") {
      steps {
        sh '''
          docker build -t example-flask-app:latest flask_app/
          '''
      }
    }

    stage("Tag Docker Image") {
      steps {
        sh '''
          docker tag example-flask-app:latest docker-registry-default.172.28.33.20.nip.io:80/myproject/example-flask-app:1.0.0
          '''
      }
    }

    stage("Login to OpenShift Integrated Repository") {
      steps {
        sh '''
          oc login 172.28.33.20:8443 -u developer -p anyvalue --insecure-skip-tls-verify=true
          docker login docker-registry-default.172.28.33.20.nip.io:80 -u developer -p $(oc whoami -t)
          '''
      }
    }

    stage("Push Image to Repository") {
      steps {
        sh '''
          docker push docker-registry-default.172.28.33.20.nip.io:80/myproject/example-flask-app:1.0.0
          '''
      }
    }

  }
}
