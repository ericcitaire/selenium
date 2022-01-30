#!/bin/bash

docker network inspect selenium >/dev/null 2>&1 || docker network create selenium
docker run \
  --network selenium \
  --name selenium-hub \
  -d \
  -p 4442-4444:4442-4444 \
  selenium/hub:4.1.1-20220121

timestamp=$(date +%s)
for browser in chrome edge firefox ; do
  docker run \
    --network selenium \
    --name ${browser} \
    -d --shm-size="2g" \
    -e SE_EVENT_BUS_HOST=selenium-hub \
    -e SE_EVENT_BUS_PUBLISH_PORT=4442 \
    -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
    selenium/node-${browser}:4.1.1-20220121
  docker run \
    --network selenium \
    --name ${browser}-video \
    -d \
    -v $(pwd)/videos:/videos \
    -e DISPLAY_CONTAINER_NAME=${browser} \
    -e FILE_NAME=${browser}_video_${timestamp}.mp4 \
    selenium/video:ffmpeg-4.3.1-20220130
done

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

for browser in chrome edge firefox ; do
  docker stop ${browser}-video && docker rm ${browser}-video
done
