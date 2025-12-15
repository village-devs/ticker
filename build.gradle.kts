plugins {
    kotlin("jvm") version "2.2.20" apply false
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "villagedevs"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }

    dependencies {
        "implementation"("org.apache.logging.log4j:log4j-core:2.25.2")
        "implementation"("org.apache.logging.log4j:log4j-api:2.25.2")
        "implementation"("org.apache.logging.log4j:log4j-slf4j2-impl:2.25.2")

        "testImplementation"(kotlin("test"))
        "testImplementation"("org.mockito:mockito-junit-jupiter:5.10.0")
        "implementation"("org.apache.commons:commons-collections4:4.5.0-M2")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}