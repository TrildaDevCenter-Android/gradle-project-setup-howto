pluginManagement {
    // Get community plugins from the Gradle Plugin Portal
    repositories.gradlePluginPortal()
}
plugins {
    id("com.gradle.develocity")
}

dependencyResolutionManagement {
    // Get components from Maven Central
    repositories.mavenCentral()
}

// Include all subfolders that contain a 'build.gradle.kts' as subprojects
rootDir.listFiles()?.filter { File(it, "build.gradle.kts").exists() }?.forEach { subproject ->
    include(subproject.name)
}

// Platform project
include(":versions")
project(":versions").projectDir = file("gradle/versions")

// Build analysis project (tasks to analyse the buils itself)
include(":build-analysis")
project(":build-analysis").projectDir = file("gradle/build-analysis")

// Configure Build Scans (local builds have to opt-in via --scan)
develocity {
    buildScan {
        termsOfUseUrl = "https://gradle.com/help/legal-terms-of-use"
        termsOfUseAgree = "yes"
        if (!providers.environmentVariable("CI").getOrElse("false").toBoolean()) {
            publishing.onlyIf { false } // only publish with explicit '--scan'
        }
    }
}
