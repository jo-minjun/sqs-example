# sqs-example

SQS 메시지 전송 Example

## 구동 방법
```shell
# 로컬 SQS 서비스를 사용하는 경우를 위해 로컬 스택 사용
./gradlew composeUp
```

## 수신 방법
```shell
# 로컬 스택 컨테이너 쉘 접근
docker container exec -it localstack /bin/bash

# 메시지 수신
while sleep 1; do awslocal sqs receive-message --queue-url http://localhost:4566/000000000000/local-example; done
```
