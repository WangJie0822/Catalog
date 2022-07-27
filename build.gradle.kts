import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `maven-publish`
    `version-catalog`
}

group = "cn.wj"
version = "1.0-SNAPSHOT"

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

catalog {
    // declare the aliases, bundles and versions in this block
    versionCatalog {
        from(files("./gradle/libs.version.toml"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cn.wj"
            artifactId = "catalog"
            version = "1.0"
            from(components["versionCatalog"])
        }
    }
    repositories {

    }
}