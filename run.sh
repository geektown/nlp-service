#!/bin/bash

# 先杀掉已经运行的nlp-service-
kill -9 $(jps | grep nlp-service- | cut -d" " -f1)
cd /home/xiaoi/nlp-service
nohup java -jar target/nlp-service-0.0.1-SNAPSHOT.jar server hello-world.yml >/dev/null 2>&1 &
