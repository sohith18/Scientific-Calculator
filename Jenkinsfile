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
        // Jenkinsfile snippet to reveal root cause
        stage('Deploy locally with Ansible') {
          steps {
            script {
              sshagent(credentials: ['ansible-ssh-key']) {
                ansiColor('xterm') { // requires AnsiColor plugin
                  withEnv([
                    'ANSIBLE_FORCE_COLOR=true',
                    'ANSIBLE_STDOUT_CALLBACK=yaml' // structured, concise output
                  ]) {
                    sh '''
                      set -euxo pipefail
                      echo "=== ansible --version ==="
                      ansible --version
                      echo "=== inventory check ==="
                      ansible-inventory -i inventory.ini --list | head -200
                      echo "=== syntax check ==="
                      ansible-playbook -i inventory.ini playbook.yml --syntax-check
                    '''
                  }
                }
                // Run playbook and capture exit code without hiding output
                def rc = sh(script: '''
                  set -o pipefail
                  ANSIBLE_FORCE_COLOR=true ANSIBLE_STDOUT_CALLBACK=yaml \
                  ansible-playbook -i inventory.ini playbook.yml -vvv
                  echo RC:$?
                ''', returnStdout: true).trim().split(':')[-1] as Integer
                echo "Ansible exit code: ${rc}"
                if (rc != 0) { error "Ansible failed with exit code ${rc}" }
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
