val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val h2Version: String by project
val mysqlVersion: String by project
val swaggerCodegenVersion: String by project
val koinKtor: String by project

plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.10"
    id("com.github.ben-manes.versions") version "0.52.0"
    id("jacoco")
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-server-request-validation:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages:$ktorVersion")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.mysql:mysql-connector-j:$mysqlVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-config-yaml:$ktorVersion")
    implementation("io.ktor:ktor-server-swagger:$ktorVersion")
    implementation("io.ktor:ktor-server-openapi:$ktorVersion")
    implementation("io.swagger.codegen.v3:swagger-codegen-generators:$swaggerCodegenVersion")
    implementation("io.insert-koin:koin-ktor3:$koinKtor")
    implementation("io.insert-koin:koin-logger-slf4j:$koinKtor")
    implementation("com.zaxxer:HikariCP:6.2.1")
    testImplementation("com.h2database:h2:$h2Version")
    testImplementation("io.insert-koin:koin-test:$koinKtor")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    testImplementation("com.ninja-squad:DbSetup:2.1.0")
    testImplementation("com.ninja-squad:DbSetup-kotlin:2.1.0")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation("org.skyscreamer:jsonassert:1.5.3")
    testImplementation("org.assertj:assertj-db:3.0.0")
}

ktor {
    fatJar {
        archiveFileName.set("todo-service.jar")
    }
}
