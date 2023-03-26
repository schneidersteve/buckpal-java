plugins {
    groovy
    id("io.micronaut.library") version "3.7.7"
}

micronaut {
    version("3.7.7")
    testRuntime("spock2")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    annotationProcessor("io.micronaut:micronaut-inject-java")
    annotationProcessor("io.micronaut.data:micronaut-data-processor")

    implementation(project(":domain"))
    implementation(project(":application"))

    implementation("io.micronaut.data:micronaut-data-r2dbc")
    implementation("jakarta.persistence:jakarta.persistence-api:3.0.0")

    runtimeOnly("ch.qos.logback:logback-classic")
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}
