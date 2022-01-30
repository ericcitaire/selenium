```
docker network create selenium
docker run \
  --network selenium \
  --name selenium-hub \
  -d \
  -p 4442-4444:4442-4444 \
  selenium/hub:4.1.1-20220121
docker run \
  --network selenium \
  --name chrome \
  -d --shm-size="2g" \
  -e SE_EVENT_BUS_HOST=selenium-hub \
  -e SE_EVENT_BUS_PUBLISH_PORT=4442 \
  -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
  selenium/node-chrome:4.1.1-20220121
docker run \
  --network selenium \
  --name firefox \
  -d --shm-size="2g" \
  -e SE_EVENT_BUS_HOST=selenium-hub \
  -e SE_EVENT_BUS_PUBLISH_PORT=4442 \
  -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
  selenium/node-firefox:4.1.1-20220121
docker run \
  --network selenium \
  --name edge \
  -d --shm-size="2g" \
  -e SE_EVENT_BUS_HOST=selenium-hub \
  -e SE_EVENT_BUS_PUBLISH_PORT=4442 \
  -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
  selenium/node-edge:4.1.1-20220121
docker run \
  --network selenium \
  --name sample \
  -d \
  -w $(pwd)/static -v $(pwd):$(pwd) \
  python:3 python -m http.server
docker run \
  --network selenium \
  --rm -it \
  -w $(pwd) -v $(pwd):$(pwd) \
  maven:3-openjdk-11 mvn -ntp clean install
```