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
        stage('Build Docker Image') {
            steps{
                sh 'docker build -t $DOCKERHUB_USR/scientific-calculator:latest .'
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('', 'dockerhub-creds') {
                        sh 'docker push $DOCKERHUB_USR/scientific-calculator:latest'
                    }
                }

                sh 'docker logout'
            }
        }
        stage('Deploy locally with Ansible') {
          steps {
            sh 'ansible-playbook -i inventory.ini playbook.yml'
          }
        }
    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
