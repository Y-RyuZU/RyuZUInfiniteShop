/*
 * This file was generated by the Gradle "init" task.
 */

plugins {
//    id ("com.github.ryuzu.java-conventions")
    id ("java-library")
}

repositories {
    mavenCentral()
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "spigot"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "Lumine Releases"
        url = uri("https://mvn.lumine.io/repository/maven-public/")
    }
}

dependencies {
    api (project(":searchableinfiniteshop-api"))
    compileOnly ("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly ("io.lumine.xikage:MythicMobs:4.12.0")
}

description = "Searchable Infinite Shop V16Older"
