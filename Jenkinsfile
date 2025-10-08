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
                sh 'docker built -t $DOCKERHUB_USR/scientific-calculator:latest .'
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
        stage('Deploy with Ansible plugin') {
          steps {
            ansiblePlaybook(
              playbook: 'playbook.yml',
              inventory: 'inventory.ini',
            )
          }
        }


    }
    post {
        always {
            junit 'target/surefire-reports/*.xml'
            emailext(
                  attachLog: true,
                  to: '$DEFAULT_RECIPIENTS',
                  subject: "Build ${currentBuild.currentResult}: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                  body: """ Job: ${env.JOB_NAME}
                            Build: ${env.BUILD_NUMBER}
                            Result: ${currentBuild.currentResult}
                            URL: ${env.BUILD_URL}
                            """,
                  recipientProviders: [
                    [$class: 'DevelopersRecipientProvider'],
                    [$class: 'RequesterRecipientProvider']
                  ]
            )
        }
    }
}


