pipeline {
    agent any
    tools {
        jdk 'java-24.0.2'
    }
    environment {
        DOCKERHUB = credentials('dockerhub-creds')
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
                sh 'mvn -B -e clean package'
            }
        }
        stage('Build Docker and Push') {
            sh """
                docker build -t ${DOCKERHUB_USR}/scientific-calculator:latest
                echo ${DOCKERHUB_PSW} | docker login -u "${DOCKERHUB_USR}" --password-stdin
                docker push ${DOCKERHUB_USR}/scientific-calculator:latest
                docker logout
            """
        }
    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
