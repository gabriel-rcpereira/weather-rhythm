node {
  stage('SCM Checkout') {
    git 'https://github.com/gabriel-rcpereira/weather-rhythm'
  }

  stage('Compile-Package') {
    sh """
      cd clean-arch 
      mvn package
      """
  }
}