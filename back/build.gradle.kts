val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project


plugins {
    application
    kotlin("jvm") version "1.5.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.0"
}

group = "codes.whytea"
version = "0.0.1"


application {
    mainClassName = "codes.whytea.ApplicationKt"
}

repositories {
    mavenCentral()

    jcenter()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")

    //Locations
    implementation("io.ktor:ktor-locations:$ktor_version")

    //Auth
    implementation("io.ktor:ktor-auth:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")

    //Server-to-server api
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization:$ktor_version")

    implementation("io.ktor:ktor-client-apache:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    //Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    //Exposed
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    implementation("com.zaxxer:HikariCP:3.4.5")
    implementation("com.h2database:h2:1.4.199")

    //Tests
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
}
