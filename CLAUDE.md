# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Full-stack Todo application with a Next.js frontend and Ktor/Kotlin backend, connected via Auth0 for authentication.

## Commands

### Frontend (`/frontend`)

```bash
pnpm dev              # Dev server
pnpm build            # Production build
pnpm lint             # ESLint
pnpm format           # Prettier
pnpm test             # Run tests once (Vitest)
pnpm test:watch       # Watch mode
pnpm test:coverage    # Coverage report
```

### Backend (`/backend`)

```bash
./gradlew check                              # Tests + ktlint (used in CI)
./gradlew test                               # Tests only
./gradlew ktlintCheck                        # Lint only
./gradlew buildFatJar                        # Build deployable JAR
./gradlew test --tests "com.example.ClassName.methodName"  # Single test
```

### Full Stack

```bash
docker compose --env-file .env.local up      # Start all services (frontend, backend, MySQL)
```

## Architecture

### Authentication Flow (Token Broker Pattern)

The Next.js frontend never exposes Auth0 tokens to the browser. Instead:

1. Browser calls Next.js API routes (e.g., `GET /api/todos`)
2. Next.js API route calls `auth0.getAccessToken()` server-side
3. Next.js forwards the request to the Ktor backend with a `Bearer` token
4. Ktor verifies the JWT and extracts `sub` as `user_id`
5. All DB queries filter by `user_id` for per-user data isolation

### Backend Structure (`/backend/src/main/kotlin/com/example/`)

- `plugins/` — Ktor plugins: JWT auth, CORS, routing, serialization, dependency injection setup
- `routes/` — HTTP endpoints (Todo CRUD)
- `dao/` — Database access objects using Exposed ORM with `suspendTransaction`
- `database/` — DB connection factory (MySQL via Exposed)
- `models/` — Data classes for requests/responses
- `config/` — AppConfig loaded from `application.yaml`
- `di/` — Koin DI module definitions (AppConfig as singleton; DatabaseFactory, TodoDao as factories)

### Frontend Structure (`/frontend/src/`)

- `app/` — Next.js App Router pages and API route handlers (the token broker lives here)
- `components/` — React components (forms, todo list, etc.)
- `lib/` — Auth0 client/server config utilities

### Database

Single `todos` table: `id` (PK), `title` (varchar 128), `description` (varchar 1024), `user_id` (varchar 256, indexed).

### Tech Stack

| Layer | Technology |
|-------|-----------|
| Frontend | Next.js 16, React 19, Tailwind CSS 4, React Hook Form |
| Auth | Auth0 (`@auth0/nextjs-auth0`) |
| Backend | Ktor 3, Kotlin, Koin DI, Exposed ORM |
| Database | MySQL 8.0 |
| Testing | Vitest + Testing Library (frontend), JUnit (backend) |
| Package manager | pnpm (frontend), Gradle (backend) |

## Environment Setup

Copy `.env.example` to `.env.local` and fill in Auth0 credentials and database connection info before running locally or with Docker Compose.
