pipeline {
    agent any
    stages {
        stage("Maven build and Deploy") {
            agent {
                docker {
                    image 'maven:3.6.3-jdk-11'
                    args '-v /home/jenkins/.m2:/root/.m2 --network=host'
                    reuseNode true
                }
            }
            steps {
                sh 'mvn -s /root/.m2/settings-docker.xml -q -U clean install -Dmaven.test.skip=true -P server'
            }
        }
    }
}
