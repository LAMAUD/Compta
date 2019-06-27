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
                mvn package
            }
        }
        stage('Deploy') {
            steps {
                mvn install
            }
        }
    }
}
