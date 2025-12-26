# Spring Boot Configuration & Environment Demo (Code & Candles)

This module demonstrates how Spring Boot loads and resolves configuration during startup:
- `application.yml` baseline defaults
- `application-{profile}.yml` overrides
- Configuration precedence (CLI > System props > Env vars > external config > classpath)
- Externalized configuration using a `./config` folder
- `@ConfigurationProperties` binding + validation
- Startup visibility using `ApplicationRunner`

## Run (local profile)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=local
Run packaged jar
mvn -q clean package
SPRING_PROFILES_ACTIVE=local java -jar target/spring-boot-configuration-1.0.0.jar
External config demo (./config)
mvn -q clean package
mkdir -p config

cat > config/application-local.yml << 'EOF'
app:
  timeout-ms: 999
  feature-x-enabled: true
EOF

SPRING_PROFILES_ACTIVE=local java -jar target/spring-boot-configuration-1.0.0.jar
Inspect resolved config at runtime
Start the app, then open:

http://localhost:8085/config

---

# Quick “Precedence Demo” Commands (for your blog screenshots)

1) Baseline:
mvn spring-boot:run -Dspring-boot.run.profiles=local
Override via env var:

APP_TIMEOUT_MS=3210 mvn spring-boot:run -Dspring-boot.run.profiles=local
Override via system property:

mvn spring-boot:run -Dspring-boot.run.profiles=local -Dapp.timeout-ms=4321
Override via CLI arg (highest):

mvn spring-boot:run -Dspring-boot.run.profiles=local -Dspring-boot.run.arguments="--app.timeout-ms=5432"
