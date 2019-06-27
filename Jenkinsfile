pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                mvn clean 
            }
        }
        stage('Test') {
            steps {
                mvn clean package
            }
        }
        stage('Deploy') {
            steps {
                mvn clean install
            }
        }
    }
}
