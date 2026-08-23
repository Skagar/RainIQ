# RainIQ

A city-scale rainwater harvesting design, compliance, and monitoring platform, built as a Spring Boot microservices system.

RainIQ lets property owners submit rooftop rainwater-harvesting designs, automatically checks those designs against rainfall-based compliance rules using live weather data, routes them to municipal officers for review, generates AI-backed design recommendations, and lets inspectors monitor installed tank sensors after approval.

---

## Architecture

10 services, each independently deployable, communicating over REST (via a shared API Gateway) and asynchronously via Kafka.

| # | Service | Port | Responsibility |
|---|---------|------|-----------------|
| 1 | Eureka Server | 8761 | Service registry / discovery |
| 2 | API Gateway | 8182 | Single entry point, routes to all services via Eureka |
| 3 | Auth Service | 8080 | Registration, login, JWT issuance, role management |
| 4 | Property Service | 8082 | Property records, activation workflow |
| 5 | Design Service | 8081 | Design submission, officer review/approval |
| 6 | Rainfall Service | 8083 | Pincode → coordinates → historical rainfall lookup |
| 7 | Compliance Service | 8084 | Rainfall-based pass/fail compliance calculation |
| 8 | AI Service | 8086 | Gemini-generated recommendations for compliant designs |
| 9 | Monitoring Service | 8085 | IoT tank device registration and reading history |
| 10 | Notification Service | 8087 | Email alerts on compliance outcome |

**Design principles:**
- **Database-per-service** — no cross-service joins or foreign keys; services reference each other by denormalized fields (e.g. `userEmail`) rather than shared tables.
- **Event-driven core workflow** — design submission fires a Kafka event; compliance checking, AI recommendation, and email notification all happen asynchronously off that event, decoupled from the request/response cycle.
- **API Gateway as the single client-facing entry point** — all external traffic goes through the gateway and is routed to the correct service via Eureka; services never expose themselves directly to clients.
- **Stateless JWT auth** — each service independently validates JWTs (role-based: `OWNER`, `MUNICIPAL_OFFICER`, `INSPECTOR`, `INTERNAL_SERVICE`); the gateway does not do auth itself.

---

## Tech Stack

- **Language/Runtime:** Java 21 (Oracle JDK), Spring Boot 3.4.4
- **Data:** PostgreSQL (one database per service), Flyway migrations
- **Security:** Spring Security 6, JWT (jjwt)
- **Messaging:** Apache Kafka
- **Service discovery / routing:** Spring Cloud Eureka, Spring Cloud Gateway (WebMVC)
- **External APIs:** Google Gemini (design recommendations), Open-Meteo (historical rainfall), Geocode.maps.co (pincode → coordinates)
- **Email:** Gmail SMTP via `spring-boot-starter-mail`
- **Observability:** Micrometer, Prometheus, Grafana
- **Containerization/Deployment:** Docker, Docker Compose, Railway
- **CI/CD:** GitHub Actions (build + test on push, Docker image build + push to Docker Hub on merge)
- **API docs:** Swagger / springdoc-openapi

---

## Core Workflow

1. **Owner** registers a property and submits a rainwater-harvesting design for it.
2. Design submission publishes a Kafka event, consumed by **Compliance Service**.
3. Compliance Service calls **Rainfall Service**, which resolves the property's pincode to coordinates and pulls historical average rainfall for that zone.
4. Based on the rainfall band, compliance checks whether the design's collection area meets the required minimum percentage of the property's total area, and marks the design `PASSED` or `FAILED`.
5. **If PASSED** → an event triggers **AI Service**, which calls Gemini to generate a written recommendation for the design.
6. **If FAILED** → an event triggers **Notification Service** directly.
7. **Municipal Officer** reviews the design. Approval is only permitted if compliance status is `PASSED`; a failed design can only be rejected.
8. **Notification Service** emails the property owner on both AI-recommendation-ready and compliance-failure outcomes.
9. Once approved, an **Inspector** can register a monitoring device against the property and log tank-level readings over time.

---

## Observability

All 8 business services expose Micrometer metrics via `/actuator/prometheus`, scraped by Prometheus and visualized in Grafana.

**Request rate (per service)**
Idle baseline of ~0.0666–0.0667 req/sec per service, sustained across all 8 services.
> Comes from Eureka health checks and Prometheus's 15s scrape interval — not user traffic. Confirms discovery and monitoring stay alive even at zero API usage; real spikes on top of this baseline correspond to actual API calls.

**P95 latency**
Request durations bucketed roughly between 0.02s and 0.29s across services.
> Most requests complete well under 300ms. The spread reflects lightweight endpoints (health checks, simple reads) versus heavier ones (design submission triggering a Kafka publish, compliance calls touching an external rainfall API).

**Error rate by status**
Live `200`, `400`, and `409` responses captured during testing.
> The `409` responses came from the review-approval guard rail — an officer attempting to approve a design whose compliance status isn't `PASSED`. Direct evidence of a business rule being enforced live, not just present in code.

**JVM heap used**
Sawtooth pattern, roughly 0–120 MB per service.
> Normal JVM behavior: heap grows with allocation and drops sharply on each garbage-collection cycle. Confirms services run with a stable, non-leaking memory profile under test load.

**Service uptime**
Constant `1` for all 8 scraped targets throughout the monitoring window.
> Every business service stayed continuously reachable by Prometheus for the full session — no crashes or restarts during testing.

---

## CI/CD

- **CI** (`ci.yml`): builds and tests all 10 services independently on every push.
- **CD** (`cd.yml`): on merge to main, builds a Docker image for each of the 10 services and pushes to Docker Hub, one image per service.
- **Deployment**: services deployed to Railway (Postgres → Kafka → Prometheus → Grafana → Eureka → Gateway → business services), using Railway's internal cross-service networking and manually constructed environment variables per service.

---

## Deployment Status

RainIQ is fully functional and verified end-to-end via Docker Compose locally (all 10 services + Kafka, PostgreSQL, Prometheus, and Grafana running together), but is **not deployed to a live, publicly accessible cloud environment**. This was a deliberate scope decision, not an oversight:

- **Railway (attempted):** Railway's free/trial tier caps a project at ~5 services. RainIQ's microservices architecture requires 12 (10 application services + supporting infra), which the trial tier cannot accommodate without either merging services (architecturally dishonest) or upgrading to a paid tier requiring a credit card.
- **AWS / Oracle Cloud free tiers:** Both require card verification even for free-tier usage, and AWS's free tier has a 12-month expiration cliff — unsuitable for a portfolio project with no ongoing budget.
- **Vercel / similar PaaS:** Structurally incompatible — no support for long-running processes, self-hosted Kafka, or self-hosted databases, all of which this architecture depends on.

Given these constraints, the project's durable deliverables are the fully working local deployment (reproducible via `docker-compose up`), the complete CI/CD pipeline (GitHub Actions building and pushing all 10 service images to Docker Hub on every push), and a recorded demo walkthrough — rather than a live URL with an unclear lifespan.

---

## Known Limitations (deliberate scope decisions)

These are intentional trade-offs made to keep the project focused and shippable, not oversights:

- **No retry path on AI/Compliance failure** — if the AI or Compliance service fails to process a design, that record is terminal; there's no automatic retry queue. The failure is persisted (status + reason) for visibility, but reprocessing would need to be manual today.
- **Manual IoT reading submission** — Monitoring Service accepts tank readings via API call rather than integrating with real hardware/sensors; this mirrors what a real device's HTTP callback would look like, without requiring physical hardware.
- **CI runs with `-DskipTests`** — `contextLoads()` and other Spring context tests require live infrastructure (Postgres, Kafka) not available on bare GitHub-hosted runners. Real integration testing (e.g. via Testcontainers) is future work, not currently implemented.
- **One active design per property is enforced at the service layer, not a database constraint** — this is deliberate, to allow a resubmission flow after rejection without needing to relax a DB-level uniqueness rule.
- **No P95/percentile histograms by default** — Micrometer's HTTP timer publishes count/sum out of the box, but not percentile buckets, unless `management.metrics.distribution.percentiles-histogram` is explicitly enabled per metric. Average latency is derived from `sum/count` instead where true percentile data isn't available.
- **Email delivery is not guaranteed** — Notification Service persists every notification attempt (including failure reason) regardless of whether the underlying SMTP send succeeds, so delivery failures are visible and auditable rather than silent, but there's no automatic retry on transient SMTP errors today.

---

## Hardships & Debugging Notes

A few non-obvious issues surfaced during local development and testing, worth documenting honestly:

- **Postgres 16 timezone bug:** Postgres 16 rejects the legacy `"Asia/Calcutta"` timezone alias that the JVM resolves to by default on some systems. Fixed by explicitly calling `TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"))` as the first line of `main()` in every service, before `SpringApplication.run()`.
- **Kafka cross-service deserialization failures:** Kafka producers serializing with type headers enabled caused consumers to attempt loading the producer's own DTO class, which doesn't exist on the consumer's classpath. Fixed by disabling `spring.json.add.type.headers` on all producers and having each consumer deserialize into its own local copy of the event class instead.
- **`localhost` inside Docker containers:** Early testing repeatedly hit connection refused errors because `localhost` inside a container refers to the container itself, not sibling containers or the host. Resolved by using Docker Compose service names for container-to-container calls, and Railway's internal hostnames (`<service>.railway.internal`) during deployment attempts.
- **Grafana dashboard breaking on volume reset:** Running `docker-compose down -v` to clear stale demo data also wipes Grafana's own volume, silently deleting its Prometheus data source connection. Dashboards then fall back to Grafana's built-in fake "TestData" source without any visible error, showing plausible-looking but entirely synthetic graphs. Root cause and fix documented, and dashboard JSON is now exported before any volume reset as a precaution.
- **SMTP `SocketException: Broken pipe` on notification send:** Email sending intermittently failed with a broken-pipe error during the STARTTLS handshake to Gmail, despite correct SMTP configuration (port 587, STARTTLS enabled) and valid credentials. Confirmed via `telnet` from inside the container that the network path to `smtp.gmail.com:587` was reachable, isolating the issue to a transient connection reset rather than a configuration or firewall problem. The failure is captured and persisted in the `notification` table with its exact stack trace for later diagnosis, rather than failing silently.

---

## Repository Structure

```
RainIQ/
├── eureka-server/
├── api-gateway/
├── auth-service/
├── property-service/
├── design-service/
├── rainfall-service/
├── compliance-service/
├── ai-service/
├── monitoring-service/
├── notification-service/
├── kafka-docker/          # docker-compose.yml, local Prometheus config
├── prometheus/            # Railway-specific Prometheus config + Dockerfile
└── .github/workflows/     # ci.yml, cd.yml
```

---

## Demo Video

📹 **[Watch the full demo walkthrough](https://drive.google.com/file/d/1CAzmlX6jcZjjE6Dw93wyqezabKaDuSL2/view?usp=drive_link)**
*(Registration → property creation → design submission → compliance check → officer review → AI recommendation → email notification → monitoring device + tank readings, all through the API Gateway.)*

---

## Concepts & Debugging Techniques Picked Up Along the Way

A few things that came up during development that weren't obvious going in — kept brief, no deep explanation:

- **`:latest` is just a tag, not a mechanism.** `docker compose up` reuses a cached local image if one already exists under that tag; only `docker compose pull` forces a check against the registry for what the tag currently points to.
- **Named volumes outlive containers.** A container is disposable; a named volume (e.g. `postgres-data`) is not — it persists across container removal/recreation. `docker compose down -v` is the only way to actually clear it.
- **Kafka's `NOT_COORDINATOR` churn on first join is normal**, not an error — coordinator discovery naturally bounces once or twice before a consumer group settles.
- **JWTs are base64, not encrypted** — the payload is always human-readable. A signature can be cryptographically verified independently (e.g. via PyJWT) against the configured secret to prove, rather than assume, whether a token is valid.
- **Kafka has a two-phase connection.** `KAFKA_BOOTSTRAP_SERVERS` only handles the initial connection; the broker then tells the client which address to use going forward via `KAFKA_ADVERTISED_LISTENERS`, which needs to be set correctly too.
- **Ports don't distinguish services on the same Docker network** — service names already do that via Docker's internal DNS. A `ports:` mapping only matters for traffic entering from outside the network.
- **`depends_on` only controls container start order**, not whether the app inside is actually ready to accept traffic yet — a Spring Boot app can still be booting after its container starts.
- **One `docker-compose.yml` doesn't merge services into a single container.** Each entry is still a fully separate container, process, and image — Compose is just an orchestration file.
- **Java preview features need `--enable-preview` at both compile time and runtime.** IntelliJ silently syncs this flag for both, masking the issue during development; a plain `java -jar` in Docker has no such flag, so it fails at startup instead with `UnsupportedClassVersionError`.
- **Docker bakes bind-mount paths in at container creation time, not at start time** — moving a mounted folder afterward doesn't update already-existing containers; they need to be recreated.
- **Containers can be OOM-killed (exit code 137)** when total memory across all running containers exceeds what the host/WSL2 has allocated — a real constraint on lower-RAM development machines running many services at once.

---



```bash
cd kafka-docker
docker-compose up -d
```

Brings up Postgres, Kafka, Prometheus, Grafana, Eureka, API Gateway, and all 8 business services. All external client requests should go through the API Gateway (`http://localhost:8182`) — individual service ports are for internal/debugging use only.

- Grafana: `http://localhost:3000`
- Prometheus: `http://localhost:9090`
- Eureka dashboard: `http://localhost:8761`
