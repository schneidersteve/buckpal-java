plugins {
    groovy
    id("io.micronaut.application") version "3.7.7"
}

micronaut {
    version("3.7.7")
    runtime("netty")
    testRuntime("spock2")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")

    implementation(project(":adapters-inbound:rest"))
    implementation(project(":adapters-outbound:persistence"))

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("io.r2dbc:r2dbc-h2")
    runtimeOnly("io.r2dbc:r2dbc-pool")

    testImplementation(project(":domain"))
    testImplementation(project(":application"))

    testImplementation("io.micronaut:micronaut-http-client")
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}

application {
    mainClass.set("buckpal.java.main.Main")
}
