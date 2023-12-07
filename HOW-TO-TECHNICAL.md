
## Build
### Maven Test
```
mvn test
```

### Maven build
```
mvn clean install -DskipTests=true
```
or

```
mvn clean package
```

### Docker build
```
docker build -f Dockerfile . -t muneer2ishtech/happeo_coding_exercise_springboot:0.6.0
```

#### Docker build multiple tags
```
docker build -f Dockerfile . -t muneer2ishtech/happeo_coding_exercise_springboot:0.6.0 -t muneer2ishtech/happeo_coding_exercise_springboot:latest
```

## Local Run
### Run using Maven
```
mvn spring-boot:run
```

### Run using already built Docker image
```
docker run -it muneer2ishtech/happeo_coding_exercise_springboot:0.6.0
```

### Run using Docker composer
- This will run PostgreSQL together

```
docker compose -f docker-compose.yml up

```

## Docker Hub
### Push to Docker Hub
- You need to sign in to Docker Hub
- Local maven and docker build should be successful

```
docker compose -f docker-compose.yml push

```

#### To push all tags to Docker Hub
```
docker image push --all-tags muneer2ishtech/happeo_coding_exercise_springboot
```

### Pull from Docker Hub
```
docker pull muneer2ishtech/happeo_coding_exercise_springboot:0.6.0
```

## Run Docker Image pulled from Docker Hub
- To download the executable docker image and run (without any local build)
- Download `public-docker-compose.yml` from [Github](https://github.com/muneer2ishtech/happeo-coding-exercise)
  - This to get docker image from [Docker Hub](https://hub.docker.com/repository/docker/muneer2ishtech/happeo_coding_exercise_springboot)
- To pull the docker image use following command
  - This will get PostgreSQL Docker image also

```
docker compose -f public-docker-compose.yml pull
```

- To Run the docker image, execute following command

```
docker compose -f public-docker-compose.yml up
```
