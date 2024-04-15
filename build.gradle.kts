import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.8.0"

    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.jetbrains.kotlinx.kover") version "0.7.6"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("kapt") version kotlinVersion
}

java.sourceCompatibility = JavaVersion.VERSION_17
version = "0.0.1"

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

apply(plugin = "java")

apply(plugin = "io.spring.dependency-management")
apply(plugin = "org.springframework.boot")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")

apply(plugin = "kotlin")
apply(plugin = "kotlin-spring") // all-open
apply(plugin = "kotlin-jpa")
apply(plugin = "kotlin-kapt")

dependencies {
    dependencyManagement {
        imports {
            mavenBom("de.codecentric:spring-boot-admin-dependencies:3.0.2")
            mavenBom("io.micrometer:micrometer-bom:1.10.5")
            mavenBom("io.micrometer:micrometer-tracing-bom:1.0.3")
        }
    }
    val springDocVersion = "2.0.4"
    val queryDslVersion = "5.0.0"
    val mapStructVersion = "1.5.5.Final"

    fun amazon(module: String, version: String? = null) =
        "software.amazon.awssdk:${module}${version?.let { ":$it" } ?: ""}"

    //	implementation(amazon("secretsmanager", "2.20.16"))
    //	implementation(amazon("ssm", "2.20.16"))
    //	implementation(amazon("elasticbeanstalk", "2.20.16"))
    //	implementation(amazon("s3", "2.20.16"))
    //	implementation("org.springframework.cloud", "spring-cloud-starter-aws", "2.2.6.RELEASE")
    //	implementation("org.springframework.boot", "spring-boot-configuration-processor", "2.6.3")
    //	implementation("com.auth0", "java-jwt", "4.3.0")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // oauth2
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.5.2.RELEASE")
    implementation("com.google.auth:google-auth-library-oauth2-http:1.18.0")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    //	implementation("org.springframework.security:spring-security-jwt:1.1.1.RELEASE")
    implementation("com.auth0", "java-jwt", "4.3.0")
    implementation("org.reflections", "reflections", "0.10.2")

    implementation("org.springframework.boot", "spring-boot-starter-actuator")
    implementation("org.springframework.boot", "spring-boot-starter-batch")
    implementation("org.springframework.boot", "spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    //	implementation("org.springframework.boot", "spring-boot-starter-data-redis")
    implementation("org.springframework.boot", "spring-boot-starter-graphql")
    implementation("com.graphql-java-kickstart:graphql-webclient-spring-boot-starter:2.0.0")
    implementation("org.springframework.boot", "spring-boot-starter-security")
    implementation("org.springframework.boot", "spring-boot-starter-validation")
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-configuration-processor")
    // implementation("com.h2database:h2")

    //	implementation("de.codecentric", "spring-boot-admin-starter-client")
    //	implementation("de.codecentric", "spring-boot-admin-starter-server")

    implementation("org.springdoc", "springdoc-openapi-starter-common", springDocVersion)
    implementation("org.springdoc", "springdoc-openapi-starter-webmvc-ui", springDocVersion)

    implementation("io.micrometer", "micrometer-observation")
    implementation("io.micrometer", "micrometer-tracing")
    implementation("io.micrometer", "micrometer-tracing-bridge-brave")
    implementation("io.micrometer", "context-propagation")

    implementation("org.jetbrains.kotlin", "kotlin-reflect", "1.8.10")
    implementation("org.jetbrains.kotlin", "kotlin-stdlib", "1.8.10")

    implementation("org.liquibase", "liquibase-core", "4.19.1")

    implementation("com.querydsl", "querydsl-jpa", queryDslVersion, classifier = "jakarta")
    implementation("com.querydsl", "querydsl-kotlin", queryDslVersion)
    kapt("com.querydsl", "querydsl-apt", queryDslVersion, classifier = "jakarta")
    implementation("jakarta.persistence", "jakarta.persistence-api")
    implementation("jakarta.annotation", "jakarta.annotation-api")
    implementation("com.tailrocks.graphql", "graphql-datetime-spring-boot-starter", "6.0.0")
    implementation("com.graphql-java:graphql-java-extended-scalars:20.0")
    // implementation("com.graphql-java-generator:graphql-java-common-runtime:1.18.6")
    implementation("org.mapstruct", "mapstruct", mapStructVersion)
    kapt("org.mapstruct", "mapstruct-processor", mapStructVersion)
    implementation("org.springframework.boot", "spring-boot-starter-mail", "3.2.4")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("io.lettuce:lettuce-core:6.3.2.RELEASE")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")

    // implementation("org.modelmapper", "modelmapper", "3.0.0")

    //	implementation("com.google.guava", "guava", "31.1-jre")
    //	api("com.google.guava", "guava", "31.1-jre")

    // mysql 관련 의존성 추가 예정
    implementation("mysql:mysql-connector-java:8.0.33")


    developmentOnly("org.springframework.boot", "spring-boot-devtools")
    annotationProcessor("org.springframework.boot", "spring-boot-configuration-processor")

    kaptTest("org.mapstruct", "mapstruct-processor", mapStructVersion)
    testImplementation("io.kotest", "kotest-runner-junit5", "5.5.5")
    testImplementation("io.kotest", "kotest-assertions-core", "5.5.5")
    testImplementation("io.kotest", "kotest-property", "5.5.5")
    testImplementation("io.kotest.extensions", "kotest-extensions-spring", "1.1.2")
    testImplementation("io.kotest.extensions", "kotest-property-arbs", "2.1.2")
    testImplementation("io.mockk", "mockk", "1.13.4")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework", "spring-webflux", "6.0.6")
    testImplementation("org.springframework.batch", "spring-batch-test")
    testImplementation("org.springframework.graphql", "spring-graphql-test")
    testImplementation("org.springframework.security", "spring-security-test")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")
    //	testRuntimeOnly("com.h2database", "h2")
    // testRuntimeOnly("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.6.4")
    // testRuntimeOnly("org.jetbrains.kotlinx", "kotlinx-coroutines-test", "1.6.4")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

allOpen {
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.Entity")
}

koverReport {
    filters {
        excludes {
            annotatedBy (
                "org.springframework.context.annotation.Configuration",
                "KoverIgnore"
            )
        }
    }
}
