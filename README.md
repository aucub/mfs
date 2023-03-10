# mfs
```
version: '3.3'
services:
  mfs:
    container_name: mfs
    restart: always
    network_mode: host
    deploy: 
      resources:
        limits:
          cpus: '0.50'
          memory: 1000M
    image: 'azumide/mfs:latest'
```
http://47.113.201.150:8080/doc.html#/home
