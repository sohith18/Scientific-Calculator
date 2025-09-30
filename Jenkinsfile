pipeline {
    agent any
    tools {
        jdk 'java-24.0.2'
    }
    triggers {
        githubPush()
    }
    stages {
//         stage('Checkout') {
//             steps {
//                 git url: 'https://github.com/sohith18/Scientific-Calculator.git',
//                     branch: 'main',
//                     credentialsId: 'sci-calc-https'
//             }
        //         }
        stage('Env check') {
            steps {
                sh 'mvn -v'
                sh 'java -version'
            }
        }
        stage('Build and Test') {
            steps {
                sh 'mvn -B clean test'
            }
        }
    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
