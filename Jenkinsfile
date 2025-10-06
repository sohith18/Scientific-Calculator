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
                script{
                    sshagent(credentials: ['ansible-ssh-key']) {
                        def exitCode = sh script: 'ansible-playbook -i inventory.ini playbook.yml', returnStatus: true
                        if (exitCode == 2) {
                            echo "WARNING: Some Ansible tasks failed or changed"
                            // Optionally continue or fail based on requirements
                        } else if (exitCode != 0) {
                            error "Ansible failed with exit code ${exitCode}"
                        }
                    }
                }
            }
        }

    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
