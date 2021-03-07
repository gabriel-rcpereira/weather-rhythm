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
    dir('clean-arch') {
      sh """
          mvn -DskipTests package
      """
      // sh """
      //     cd clean-arch
      //     mvn -DskipTests package
      // """
    }
  }

  stage('Create and push container') {
    dir('clean-arch') {
      def builtContainer = docker.build 'gabrielrcpereira/weather-rhythm'
      builtContainer.push()
      builtContainer.push 'latest'
    }

    // sh """        
    //     docker build -t api/weather-rhythm .
    //     docker tag api/weather-rhythm:latest gabrielrcpereira/weather-rhythm
    //     docker push gabrielrcpereira/weather-rhythm
    // """
  }

  stage('Deploy') {
    sh """        
        kubectl apply -f test-pod.yml
    """
  }

}