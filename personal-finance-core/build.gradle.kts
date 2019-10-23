object DependencyVersions {
    const val SPRING_VERSION = "2.1.7.RELEASE"
}

dependencies {
//    implementation("com.github.derjust:spring-data-dynamodb:5.1.0")
    implementation("org.springframework.boot:spring-boot-starter:${DependencyVersions.SPRING_VERSION}")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:${DependencyVersions.SPRING_VERSION}")
    testImplementation("io.projectreactor:reactor-test:3.2.11.RELEASE")
    testImplementation("io.projectreactor:reactor-test:3.2.11.RELEASE")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${DependencyVersions.SPRING_VERSION}")
}