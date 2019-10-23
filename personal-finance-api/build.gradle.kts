import org.springframework.boot.gradle.tasks.run.BootRun

object DependencyVersions {
    const val SPRING_VERSION = "2.1.7.RELEASE"
}

plugins {
    application
}

apply(plugin = "org.springframework.boot")
apply(plugin = "application")

tasks.getByName<BootRun>("bootRun") {
    main = "com.coelhocaique.finance.api.PersonalFinanceApiApplication.kt"
    systemProperties(System.getProperties().mapKeys { it.key as String })
}

application{
    mainClassName = "ps.chargeback.paymentsearch.api.PaymentSearchApplicationKt"
}

tasks.register<Copy>("copyFiles"){
    from(file("../docker/scripts"))
    into("${project.buildDir}/docker/scripts")
}

tasks.getByName<Zip>("distZip").enabled = false
tasks.getByName<Tar>("distTar").enabled = false
tasks.getByName("bootDistZip").dependsOn(tasks.getByName("copyFiles"))

dependencies {
    compile(project(":personal-finance-core"))
//    implementation("com.github.derjust:spring-data-dynamodb:5.1.0")
    implementation("org.springframework.boot:spring-boot-starter-webflux:${DependencyVersions.SPRING_VERSION}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${DependencyVersions.SPRING_VERSION}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.9")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${DependencyVersions.SPRING_VERSION}")
}