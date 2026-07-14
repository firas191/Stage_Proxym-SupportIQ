# Backend — Spring Boot 3 (généré au J2, semaine 1)

## Génération (Spring Initializr)
```bash
curl https://start.spring.io/starter.zip \
  -d type=maven-project -d language=java -d javaVersion=21 \
  -d bootVersion=3.4.1 \
  -d groupId=com.supportiq -d artifactId=backend -d name=supportiq-backend \
  -d dependencies=web,security,data-jpa,postgresql,flyway,validation,amqp,websocket,actuator,lombok \
  -o backend.zip && unzip backend.zip -d .
```

## Dépendances à ajouter manuellement au pom.xml
- `io.jsonwebtoken:jjwt-api / jjwt-impl / jjwt-jackson` (0.12.x) — JWT
- `com.opencsv:opencsv` — parsing CSV en streaming
- `com.bucket4j:bucket4j-core` — rate limiting du webhook
- `org.testcontainers:postgresql` + `junit-jupiter` (test) — tests d'intégration
- `com.github.ben-manes.caffeine:caffeine` — cache dashboard

## Conventions
- Migrations : `src/main/resources/db/migration/V1__users_auth.sql`, `V2__tickets_imports.sql`...
- Erreurs : ProblemDetail (RFC 7807) via un @RestControllerAdvice global
- Un package par module : auth / imports / tickets / dashboard / messaging / webhook
