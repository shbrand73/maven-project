pipeline  {
	agent any
	stages{
		stage('Build'){
			steps  {
				bat 'mvn clean package'
				bat "docker build . -t tomcatWebApp:${env.BUILD_ID}
			}
		}
	}