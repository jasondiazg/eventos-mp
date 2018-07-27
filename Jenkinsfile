def app
pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean package -DskipTests'
      }
    }
    stage('Test') {
      steps {
        sh 'mvn verify'
      }
    }
    stage('Deploy:develop') {
        when {
            branch 'develop'
        }
        steps {
            echo "Deploying from branch develop"
            sh 'cp target/eventos-mp.war ~/payara41/glassfish/domains/payaradomain/autodeploy/'
        }    
    }
    stage('Docker:develop') {
        when {
            branch 'develop'
        }
        steps {
            echo "Uploading built image to latest"
            //sh 'docker build -t eventos-mp-micro .'
            //sh 'docker tag eventos-mp-micro jasondiazg/eventos-mp-micro:$(git rev-parse --short HEAD)'
            app = docker.build("jasondiazg/eventos-mp-micro")
            docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                app.push("eventos-mp-micro")
                app.push("latest")
                //sh 'docker push jasondiazg/eventos-mp-micro'
            }
        }    
    }
    stage('Deploy:master') {
        when {
            branch 'master'
        }
        steps {
            echo "Deploying from branch master"
            sh 'cp target/eventos-mp.war ~/payara41/glassfish/domains/payaradomain/autodeploy/'
        }     
    }
  }
}
