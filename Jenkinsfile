node {
  stage('SCM Checkout') {
    git 'https://github.com/gabriel-rcpereira/weather-rhythm'
  }

  // stage('SonarQube Analysis') {
  //   withSonarQubeEnv('sonar') {
  //     sh """
  //       cd clean-arch
  //       mvn sonar:sonar
  //     """
  //     //sh "mvn sonar:sonar"
  //   }
  // }

  // stage('Quality Gate') {
  //   timeout(time: 1, unit: 'HOURS') {
  //     def qg = waitForQualityGate()
  //     if (qg.status != 'OK') {
  //       error "Pipeline aborted due to quality gate failure: ${qg.status}"
  //     }
  //   }
  // }  

  stage('Compile-Package') {
    sh """
        cd clean-arch
        mvn -DskipTests package
    """
  }

  stage('Create and push container') {
    sh """        
        cd clean-arch
    """

    def builtContainer = docker.build 'api/weather-rhythm'
    builtContainer.push()
  }

  stage('Deploy') {
    sh """        
        kubectl apply -f test-pod.yml
    """
  }

}
