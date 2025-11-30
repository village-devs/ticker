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
        "testImplementation"(kotlin("test"))
        "testImplementation"("org.mockito:mockito-junit-jupiter:5.10.0")
        "implementation"("org.apache.commons:commons-collections4:4.5.0-M2")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}