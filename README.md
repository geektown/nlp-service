# nlp-service
dropwizard Micro-service for nlp

# How to run
```mvn package```

```java -jar target/nlp-service-0.0.1-SNAPSHOT.jar server hello-world.yml```

# Test 
`curl http://localhost:8080/recognition?corpus=习近平主席在人民大会堂会见了美国总统奥巴马。`

`curl http://localhost:8080/hello-world?name=geektown`
