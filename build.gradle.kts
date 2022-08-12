plugins {
    `maven-publish`
    `version-catalog`
    signing
}

val signingProp = java.util.Properties().apply {
    load(rootProject.file("signing.properties").inputStream())
}

group = "io.github.wangjie0822"
version = "gradle.7.4.2-1.2.0"

catalog {
    // declare the aliases, bundles and versions in this block
    versionCatalog {
        from(files("./gradle/libs.version.toml"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "catalog"
            from(components["versionCatalog"])
        }
    }
    repositories {
        mavenLocal()
        maven {
            name = "mavenCentral"
            val url = if (version.toString().endsWith("SNAPSHOT")) {
                "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            } else {
                "https://s01.oss.sonatype.org/content/repositories/releases/"
            }
            setUrl(url)
            credentials {
                username = signingProp.getProperty("mavenCentralUsername")
                password = signingProp.getProperty("mavenCentralPassword")
            }

        }
    }
}

ext.set("signing.keyId", signingProp.getProperty("signing.keyId"))
ext.set("signing.password", signingProp.getProperty("signing.password"))
ext.set("signing.secretKeyRingFile", signingProp.getProperty("signing.secretKeyRingFile"))

signing {
    sign(publishing.publications)
}