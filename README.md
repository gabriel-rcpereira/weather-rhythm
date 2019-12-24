# Weather Rhythm API

The API purpose is looking for kinds of music by the temperature of the places desire.

The rhythms will be chosen considering:

    1. If temperature (celcius) is above 30 degrees, suggest tracks for party;

    2. In case temperature is between 15 and 30 degrees, suggest pop music tracks;

    3. If it's a bit chilly (between 10 and 14 degrees), suggest rock music tracks;

    4. Otherwise, if it's freezing outside, suggests classical music tracks;

## Prerequisites

These are the Dev Dependencies:

    - [Gradle 4.0.1 or Later](https://gradle.org/install/)

    - [Java 11](https://openjdk.java.net/projects/jdk/11/)

    - [Enable the Lombok within the IDE](https://projectlombok.org/)

## Deployment

The **dockerfile** in the root folder contains the parameter to build the docker image. The variable **APP_HOME** contains
the local path where the jar place.

The docker command below creates the docker image:

`docker image build -t weather.rhythm:1.0.0 .`

## Build With

    - [Spring Boot 2.2.2](https://spring.io/projects/spring-boot)

    - [Spring Cloud OpenFeign](https://cloud.spring.io/spring-cloud-openfeign/reference/html/)

    - [Spotify Web Api](https://github.com/thelinmichael/spotify-web-api-java)

    - [SpringFox Swagger 2](https://springfox.github.io/springfox/docs/current/)

    - [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)

    - [Mokito](https://site.mockito.org/)
