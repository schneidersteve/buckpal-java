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

    implementation(project(":domain"))
    implementation(project(":application"))

    implementation("io.micronaut:micronaut-http-server-netty")

    testImplementation("org.spockframework:spock-core")
    testImplementation("io.micronaut:micronaut-http-client")

    runtimeOnly("ch.qos.logback:logback-classic")
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}
