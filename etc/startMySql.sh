#!/usr/bin/env bash
docker stop mysql
docker rm mysql
docker run --name mysql \
        -p 3306:3306 \
        -e MYSQL_ROOT_PASSWORD=admin123 \
        -e MYSQL_USER=user \
        -e MYSQL_PASSWORD=admin123 \
        -e MYSQL_DATABASE=springcore \
        -d mysql:latest
