#!/bin/bash

# first terminate any old ones
docker kill citest-651
docker rm citest-651

# now run the new one
docker run -d --name citest-651 -p 4444:4444 -t citest ./gradlew :server:run --args "4444"

