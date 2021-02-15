node {
  stage('SCM Checkout') {
    git 'https://github.com/gabriel-rcpereira/weather-rhythm'
  }

  stage('SonarQube Analysis') {
    withSonarQubeEnv('sonar') {
      sh """
        cd clean-arch
        mvn sonar:sonar
        """
      //sh "mvn sonar:sonar"
    }
  }

  stage('Compile-Package') {
    sh """
        cd clean-arch
        mvn package
        """
    // sh "cd clean-arch"
    // sh "mvn package"
  }

}