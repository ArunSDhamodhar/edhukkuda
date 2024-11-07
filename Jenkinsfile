pipeline {

agent any


environment {

DOCKER_CREDENTIALS_ID = 'docker-hub-credentials' // Replace with your Docker ID credentials

DOCKER_USERNAME = 'arunsdhamodhar' // Replace with your Docker Hub username

}

  environment {
        PATH = "/usr/local/bin:${env.PATH}" // Adjust if necessary
    }

    stages {
        stage('Build Docker Image') {
            steps {
                sh 'docker build -t arunsdhamodhar/demo-app:16 .'
            }
        }
    }
 tools {
        maven 'Maven 3.x' // Use the name you provided in Global Tool Configuration
    }

stages {

stage('Checkout') {

steps {

checkout scm

}

}


stage('Build') {

steps {

sh 'mvn clean package -DskipTests'

}

}


stage('Test') {

steps {

sh 'mvn test'

}

}


stage('Build Docker Image') {

steps {

script {

docker.build("${DOCKER_USERNAME}/demo-app:${BUILD_NUMBER}")

}

}

}


stage('Push Docker Image') {

steps {

script {

docker.withRegistry('https://registry.hub.docker.com', DOCKER_CREDENTIALS_ID) {

docker.image("${DOCKER_USERNAME}/demo-app:${BUILD_NUMBER}").push()

}

}

}

}


stage('Deploy') {

steps {

sh 'docker-compose up -d'

}

}

}


post {

always {

sh 'docker-compose down'

}

}

}
