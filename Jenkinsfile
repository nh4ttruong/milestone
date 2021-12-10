pipeline {
  agent any

  tools {
    jdk 'jdk-11'
    maven 'mvn-3.6.3'
  }

  environment {
    HEROKU_API_KEY = credentials('sprint-boot-milestone')
  }

  stages {
    stage('Build') {
      steps {
        withMaven(maven : 'mvn-3.6.3') {
          sh "mvn package"
        }
      }
    }
    
    stage ('OWASP Dependency-Check Vulnerabilities') {
      steps {
        withMaven(maven : 'mvn-3.6.3') {
          sh 'mvn dependency-check:check'
        }

        dependencyCheckPublisher pattern: 'target/dependency-check-report.xml'
      }
    }

    stage('SonarQube analysis') {
      steps {
        withSonarQubeEnv(credentialsId: 'sonarqube-secret', installationName: 'sonarqube-server') {
          withMaven(maven : 'mvn-3.6.3') {
            sh 'mvn sonar:sonar -Dsonar.dependencyCheck.jsonReportPath=target/dependency-check-report.json -Dsonar.dependencyCheck.xmlReportPath=target/dependency-check-report.xml -Dsonar.dependencyCheck.htmlReportPath=target/dependency-check-report.html'
          }
        }
      }
    }
    
    stage("Quality Gate") {
            steps {
              timeout(time: 10, unit: 'MINUTES') {
                waitForQualityGate abortPipeline: true
          }
       }
    }

    stage('Heroku Deploy') {
      steps {
        withMaven(maven: 'mvn-3.6.3') {
          sh 'mvn clean heroku:deploy'
        }
      }
    }
    
  }
}
