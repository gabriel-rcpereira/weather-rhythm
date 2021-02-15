node {
  stage('SCM Checkout') {
    git 'https://github.com/gabriel-rcpereira/weather-rhythm'
  }

  stage('Compile-Package') {
    sh "cd clean-arch"
    sh "mvn package"
  }

  stage('SonarQube Analysis') {
    withSonarQubeEnv('sonar') {
      sh "mvn sonar:sonar"
    }
  }
}