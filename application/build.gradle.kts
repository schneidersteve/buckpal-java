plugins {
    groovy
    id("io.micronaut.library") version "3.7.7"
}

micronaut {
    version("3.7.7")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.26")
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    annotationProcessor("io.micronaut:micronaut-inject-java")

    implementation(project(":domain"))

    implementation("javax.transaction:javax.transaction-api:1.3")

    testImplementation("org.spockframework:spock-core")
    testRuntimeOnly("net.bytebuddy:byte-buddy:1.12.18")
    testRuntimeOnly("org.objenesis:objenesis:3.3")
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}
