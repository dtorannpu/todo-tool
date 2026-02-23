# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A full-stack Todo application using Next.js (frontend) and Ktor/Kotlin (backend) with Auth0 for authentication and MySQL for persistence. All services run via Docker Compose.

## Commands

### Running the Application

```bash
# Start all services (requires .env.local from .env.example)
docker compose --env-file .env.local up
```

### Frontend (`frontend/`)

```bash
npm run dev          # Dev server with Turbopack + Node inspect
npm run build        # Production build
npm run lint         # ESLint
npm run format       # Prettier format
npm test             # Vitest (single run)
npm run test:watch   # Vitest watch mode
npm run test:coverage
```

### Backend (`backend/`)

```bash
./gradlew check          # Run all checks: tests + ktlint
./gradlew test           # Tests only
./gradlew ktlintCheck    # Lint only
./gradlew buildFatJar    # Build todo-service.jar
```

To run a single backend test class:
```bash
./gradlew test --tests "com.example.SomeTestClass"
```

## Architecture

### Request Flow

```
Browser → Next.js Frontend (port 3000)
         → Next.js API Routes (/api/todos/*)    # token broker
         → Ktor Backend (port 8080)             # JWT verification
         → MySQL Database
```

The Next.js API routes act as a **token broker**: they call `auth0.getAccessToken()` server-side and forward requests to the Ktor backend with an `Authorization: Bearer <token>` header. This keeps Auth0 tokens out of the browser.

### Authentication

Auth0 is the identity provider. The middleware in [frontend/src/proxy.ts](frontend/src/proxy.ts) protects all routes except `/auth/*` and static assets. Auth0's `nextjs-auth0` SDK manages sessions on the frontend.

The Ktor backend ([backend/src/main/kotlin/com/example/plugins/JWT.kt](backend/src/main/kotlin/com/example/plugins/JWT.kt)) verifies JWTs by fetching Auth0's JWK set. It extracts the user identity from the JWT `sub` claim and passes it to the DAO layer to enforce per-user data isolation.

### Backend Structure (Ktor + Koin DI)

- **Entry point**: [Application.kt](backend/src/main/kotlin/com/example/Application.kt) — installs Koin, connects DB, registers plugins and routes
- **DI**: [di/AppModule.kt](backend/src/main/kotlin/com/example/di/AppModule.kt) — `AppConfig` (singleton), `DatabaseFactory`, `TodoDao` (factories)
- **Plugins**: `plugins/` — JWT auth, CORS/HTTP, serialization (Kotlinx), request validation, status pages
- **Routes**: [routes/TodoRoutes.kt](backend/src/main/kotlin/com/example/routes/TodoRoutes.kt) — all `/todos` endpoints wrapped in `authenticate("auth0")`
- **DAO**: [dao/TodoDaoImpl.kt](backend/src/main/kotlin/com/example/dao/TodoDaoImpl.kt) — Exposed ORM, all queries filter by `user_id`
- **Models**: [models/Todo.kt](backend/src/main/kotlin/com/example/models/Todo.kt) — `Todo`, `CreateTodo` (Kotlinx Serializable), `Todos` (Exposed Table)

Database operations use `suspendTransaction` via [dao/DbQuery.kt](backend/src/main/kotlin/com/example/dao/DbQuery.kt) for coroutine compatibility.

### Frontend Structure (Next.js App Router)

- **Middleware**: [src/proxy.ts](frontend/src/proxy.ts) — auth guard for all routes
- **Auth client**: [src/lib/auth0.ts](frontend/src/lib/auth0.ts) — Auth0 client config, redirects to `/todo` post-login
- **Pages**: `app/page.tsx` (landing), `app/todo/page.tsx` (list, server component), `app/todo/[id]/page.tsx` (detail, client component with React Hook Form)
- **API routes**: `app/api/todos/` — proxy to backend, adding Auth0 bearer token
- **Forms**: [src/components/TodoForm.tsx](frontend/src/components/TodoForm.tsx) — React Hook Form with client-side validation matching backend constraints (title ≤128, description ≤1024)

### Database Schema

Single `todos` table: `id` (PK auto-increment), `title` (varchar 128), `description` (varchar 1024), `user_id` (varchar 256, indexed).

### Environment Variables

Copy `.env.example` to `.env.local`. Required variables:
- `AUTH0_SECRET`, `AUTH0_ISSUER_BASE_URL`, `AUTH0_CLIENT_ID`, `AUTH0_CLIENT_SECRET`, `AUTH0_AUDIENCE`, `AUTH0_DOMAIN`
- `APP_BASE_URL` — frontend URL
- `API_URL` — backend URL (used server-side in Next.js)
- `DATABASE_URL`, `DATABASE_USER`, `DATABASE_PASSWORD`

Auth0 setup requires a Regular Web Application with callback URL `{APP_BASE_URL}/auth/callback` and logout URL `{APP_BASE_URL}`.
