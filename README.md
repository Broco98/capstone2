# 사용법

resources 밑에 3개의 파일이 필요합니다.

application-aws.yml
```yml
cloud:
  aws:
    credentials:
      accessKey: {key}
      secretKey: {key}
    region:
      static: {region} # 버킷 region
    s3:
      bucket: {name}  # 버킷 이름
    stack:
      auto: false
```
application-ai.yml
```yml
spring:
  ai:
    openai:
      api-key: {key}
      chat:
        options:
          model: gpt-4o
          temperature: 0.4
          response-format: json_object
      image:
        options:
          model: dall-e-3
          n: 1
          size: 1024x1024
          quality: standard # standard
```
application-prod.yml
```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: {mysql url}
    username: {name}
    password: {password}

  jpa:
    hibernate:
      ddl-auto: {update or create etc ...}
    properties:
      hibernate:
        format_sql: true
        default_batch_fetch_size: 1000

  data:
    redis:
      host: {your host}
```

