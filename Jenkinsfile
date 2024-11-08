pipeline {
    agent any

    environment {
        PATH = "/usr/local/bin:${env.PATH}" // Adjust if necessary
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials' // Replace with your Docker Hub credentials ID
        DOCKER_USERNAME = 'arunsdhamodhar' // Replace with your Docker Hub username
    }

    tools {
        maven 'Maven 3.x' // Use the name you provided in Global Tool Configuration
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm // Checkout the source code from the configured SCM
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests' // Build the project and skip tests
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test' // Run tests
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([string(credentialsId: 'mytoken', variable: 'TOKEN')]) { // Use Jenkins credentials for Docker login
                    sh '''
                        echo "Logging into Docker..."
                        docker login -u '${DOCKER_USERNAME}' -p "$TOKEN"
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker image..."
                    def dockerImage = docker.build("${DOCKER_USERNAME}/demo-app:${env.BUILD_NUMBER}") // Build the Docker image
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    echo "Pushing Docker image to Docker Hub..."
                    docker.withRegistry('https://registry.hub.docker.com', DOCKER_CREDENTIALS_ID) {
                        docker.image("${DOCKER_USERNAME}/demo-app:${env.BUILD_NUMBER}").push() // Push the image to Docker Hub
                        docker.image("${DOCKER_USERNAME}/demo-app:${env.BUILD_NUMBER}").push("latest") // Optionally push as latest
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "Cloning deployment repository..."
                    sh 'git clone https://github.com/ArunSDhamodhar/microservices-deployment.git' // Clone the deployment repo
                    dir('microservices-deployment') { // Change directory to the cloned repo
                        echo "Running Docker Compose to start services..."
                        sh 'docker-compose up -d' // Start services using Docker Compose
                    }
                }
            }
        }
    }

    post {
        always {
            script {
                echo "Tearing down services..."
                sh 'docker-compose down' // Stop and remove containers defined in the Compose file
            }
        }
    }
}
