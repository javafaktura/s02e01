#!/usr/bin/env bash
docker exec -i mysql sh \
        -c 'exec mysql -uroot -p"admin123"' < ./init_schema.sql
