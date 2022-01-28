```
docker network create selenium
docker run --network selenium --name chrome -d --shm-size="2g" selenium/standalone-chrome:4.1.1-20220121
docker run --network selenium --name sample -d -w $pwd/static -v $(pwd):$(pwd) python:3 python -m http.server
docker run --network selenium --rm -it -w $(pwd) -v $(pwd):$(pwd) maven:3-openjdk-11 mvn -ntp clean install
```