import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	id("org.springframework.boot") version "2.1.7.RELEASE" apply false
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.3.41"
    kotlin("jvm") version "1.3.41"
}

repositories {
    mavenCentral()
}

subprojects{
    apply(plugin="java")
    apply(plugin="kotlin")
    apply(plugin="org.jetbrains.kotlin.plugin.spring")
    apply(plugin="io.spring.dependency-management")

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
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-RC2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.3.0-RC2")
        developmentOnly("org.springframework.boot:spring-boot-devtools")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.projectreactor:reactor-test")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    the<DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES){
                bomProperty("kotlin.version", "1.3.41")
            }
        }
    }
}

project(":personal-finance-core"){
    dependencies {
        implementation("org.springframework.boot:spring-boot")
        implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}

project(":personal-finance-api"){
    apply(plugin = "org.springframework.boot")

    tasks.getByName<BootRun>("bootRun"){
        main = "com.coelhocaique.finance.api.PersonalFinanceApiApplicationKt"
    }

    dependencies {
        compile(project(":personal-finance-core"))
        implementation("io.springfox:springfox-swagger2:2.9.2")
        implementation("io.springfox:springfox-swagger-ui:2.9.2")
        implementation("org.springframework.boot:spring-boot-starter-hateoas")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    }
}