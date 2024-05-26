import org.example.gradle.tasks.MD5DirectoryChecksum

plugins {
    id("application")               // For stand-alone application packaging
    id("org.example.gradle.sbom")
    id("org.example.gradle.reports")
    id("org.example.gradle.end2end-testing")
    id("org.example.gradle.java")
    id("org.example.gradle.war")    // For web application packaging/deployment
}

// Generate additional resources required at application runtime
val resourcesChecksum = tasks.register<MD5DirectoryChecksum>("resourcesChecksum") {
    inputDirectory = layout.projectDirectory.dir("src/main/resources")
    checksumFile = layout.buildDirectory.file("generated-resources/md5/resources.MD5")
}
tasks.processResources { from(resourcesChecksum) }
