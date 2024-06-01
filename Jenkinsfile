pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    }
    stages {
        stage('Maven build') {
            steps {
                 sh 'mvn -U clean install -Dmaven.test.skip=true -P dev'
            }
        }
        stage("Docker build") {
            steps {
                sh "docker build -t anhdai0801/dat-dut-backend:latest ."
            }
        }
        stage("Docker push") {
            steps {
                sh "echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin"
                sh "docker push anhdai0801/dat-dut-backend:latest"
                sh "docker rmi anhdai0801/dat-dut-backend:latest"
            }
        }
    }
}