pipeline {
    agent any

    parameters {
         string(name: 'tomcat_dev', defaultValue: 'localhost:8090', description: 'Staging Server')
         string(name: 'tomcat_prod', defaultValue: 'localhost:9090', description: 'Production Server')
    }

    triggers {
         pollSCM('* * * * *')
     }

stages{
        stage('Build'){
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                    echo 'Now Archiving...'
                    archiveArtifacts artifacts: '**/target/*.war'
                }
            }
        }

        stage ('Deployments'){
            parallel{
                stage ('Deploy to Staging'){
                    steps {
                        bat "**/target/*.war ${params.tomcat_dev}:\opt\apache-tomcat-8.5.35-staging\webapps"
                    }
                }

                stage ("Deploy to Production"){
                    steps {
                        bat "**/target/*.war @${params.tomcat_prod}:\opt\apache-tomcat-8.5.35-prod\webapps"
                    }
                }
            }
        }
    }
}