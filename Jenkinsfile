def branchName = ''
def unixTime = ''
def developmentTag = ''
pipeline {
    agent any
    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        ENVIRONMENT        = 'dev'
    }
    stages {
        stage('Maven build') {
            steps {
                 sh 'mvn -U clean install -Dmaven.test.skip=true -P dev'
            }
        }
        stage("Docker build") {
            steps {
                script {
                    branchName = env.ENVIRONMENT
                    unixTime = (new Date().time / 1000) as Integer
                    developmentTag = "${branchName}-${unixTime}"
                }
                sh "docker build -t anhdai0801/dat-dut-backend:${developmentTag} ."
            }
        }
        stage("Docker push") {
            steps {
                sh "echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin"
                sh "docker push anhdai0801/dat-dut-backend:${developmentTag}"
            }
        }
        stage ('Deploy with docker') {
            steps{
                sh "docker pull anhdai0801/dat-dut-backend:latest"
                sh "docker stop backend || true && docker rm backend || true"
                sh "docker compose up -d"
            }
        }
    }
}