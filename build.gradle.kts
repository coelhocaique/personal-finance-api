import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.1.7.RELEASE" apply false
    id("org.sonarqube") version "2.7.1"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.41"
    kotlin("jvm") version "1.3.41"
}

repositories {
    mavenCentral()
}

subprojects{
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    group = "com.coelhocaique"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11

    val developmentOnly by configurations.creating
    configurations {
        runtimeClasspath {
            extendsFrom(developmentOnly)
        }
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    repositories {
        mavenCentral()
    }

    dependencies {

        implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.41")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.41")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:1.3.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.3.1")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.0.pr3")
        implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.10.0.pr3")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.10.0.pr3")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.10.0.pr3")

        testImplementation("org.junit.jupiter:junit-jupiter:5.5.1")
        testImplementation("io.mockk:mockk:1.9.3")
        testImplementation("io.projectreactor:reactor-test:3.2.11.RELEASE")
    }

    java.sourceCompatibility = JavaVersion.VERSION_11
    java.targetCompatibility = JavaVersion.VERSION_11

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
}