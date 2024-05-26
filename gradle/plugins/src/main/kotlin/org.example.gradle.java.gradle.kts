import com.autonomousapps.tasks.ProjectHealthTask
import gradle.kotlin.dsl.accessors._e51b323cfb2e2618e566e57940084292.java
import io.fuchs.gradle.collisiondetector.DetectCollisionsTask
import org.example.gradle.tasks.DependencyFormatCheck

plugins {
    id("java")
    id("jacoco") // Record test coverage data during test execution
    id("org.example.gradle.base")
    id("org.example.gradle.dependency-check-project")
}

// Tweak 'lifecycle tasks': These are the tasks in the 'build' group that are used in daily development. Under normal
// circumstances, these should be all the tasks developers needs in their daily work.
tasks.named("qualityCheck") {
    dependsOn(tasks.withType<JavaCompile>())
}

// Clear tasks group 'build' from clutter for a clean set of tasks to be used in daily work
tasks.buildDependents { setGroup(null) }
tasks.buildNeeded { setGroup(null) }
tasks.jar { setGroup(null)  }
sourceSets.all {
    tasks.named(classesTaskName) { group = null }
}

// Configure build wide consistent resolution. That is, the versions that are used on the runtime classpath of the
// web applications should also be used in all other places (e.g. also when compiling a project at the bottom of the
// dependency graph that does not see most of the other dependencies that may influence the version choices).
jvmDependencyConflicts.consistentResolution {
    platform(":versions")
    providesVersions(":app")
}

// Configure which JDK and Java version to build with. The version is defined in 'gradle/jdk-version.txt' so that
// GitHub actions can also pick it up from there.
java {
    toolchain.languageVersion = JavaLanguageVersion.of(providers.fileContents(
        rootProject.layout.projectDirectory.file("gradle/jdk-version.txt")
    ).asText.get().trim())
}

// Build/publish with sources and Javadoc
java {
    withSourcesJar()
    withJavadocJar()
}
tasks.named("sourcesJar") { group = null }
tasks.named("javadocJar") { group = null }

// Configuration to make the build reproducible. This means we override settings that are, by default, platform
// dependent (e.g. different default encoding on Windows and Unix systems).
tasks.withType<JavaCompile>().configureEach {
    options.apply {
        isFork = true
        encoding = "UTF-8"
        compilerArgs.add("-implicit:none")
        compilerArgs.add("-Werror")
        compilerArgs.add("-Xlint:all,-serial")
    }
}
tasks.withType<Javadoc>().configureEach {
    options {
        this as StandardJavadocDocletOptions
        encoding = "UTF-8"
        addStringOption("Xwerror", "-Xdoclint:all,-missing")
    }
}
tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
    filePermissions { unix("0664") }
    dirPermissions { unix("0775") }
}

// Configure details for *all* test executions directly on 'Test' task
tasks.test {
    group = "build"

    useJUnitPlatform() // Use JUnit 5 as test framework
    maxParallelForks = 4

    testLogging.showStandardStreams = true

    maxHeapSize = "1g"
    systemProperty("file.encoding", "UTF-8")
}

// Configure common test runtime dependencies for *all* projects
dependencies {
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.slf4j:slf4j-simple")
}
