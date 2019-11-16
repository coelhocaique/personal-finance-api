object DependencyVersions {
    const val SPRING_VERSION = "2.1.7.RELEASE"
}

dependencies {
    implementation("com.amazonaws:aws-java-sdk-dynamodb:1.11.675")
    implementation("org.springframework.boot:spring-boot-starter:${DependencyVersions.SPRING_VERSION}")
    testImplementation("io.projectreactor:reactor-test:3.2.11.RELEASE")
    testImplementation("io.projectreactor:reactor-test:3.2.11.RELEASE")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${DependencyVersions.SPRING_VERSION}")
}