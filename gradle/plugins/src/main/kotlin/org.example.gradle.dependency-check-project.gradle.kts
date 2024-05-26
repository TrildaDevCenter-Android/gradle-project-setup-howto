import com.autonomousapps.tasks.ProjectHealthTask
import org.example.gradle.tasks.DependencyFormatCheck

plugins {
    id("java")
    id("io.fuchs.gradle.classpath-collision-detector")
}

val checkDependencyFormatting = tasks.register<DependencyFormatCheck>("checkDependencyFormatting") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP

    buildFilePath = project.buildFile.absolutePath
    shouldNotHaveVersions = true
    sourceSets.all {
        declaredDependencies.put(implementationConfigurationName, provider { configurations.getByName(implementationConfigurationName).dependencies.map { d -> d.toDeclaredString() } })
        declaredDependencies.put(runtimeOnlyConfigurationName, provider { configurations.getByName(runtimeOnlyConfigurationName).dependencies.map { d -> d.toDeclaredString() } })
        declaredDependencies.put(compileOnlyConfigurationName, provider { configurations.getByName(compileOnlyConfigurationName).dependencies.map { d -> d.toDeclaredString() } })
        declaredDependencies.put(apiConfigurationName, provider { configurations.findByName(apiConfigurationName)?.dependencies?.map { d -> d.toDeclaredString() } ?: emptyList() })
        declaredDependencies.put(compileOnlyApiConfigurationName, provider { configurations.findByName(compileOnlyApiConfigurationName)?.dependencies?.map { d -> d.toDeclaredString() } ?: emptyList() })
    }
}

tasks.named("qualityCheck") {
    dependsOn(checkDependencyFormatting)
    dependsOn(tasks.detectCollisions)
    dependsOn(tasks.withType<ProjectHealthTask>())
}

fun Dependency.toDeclaredString() = when(this) {
    is ProjectDependency -> ":$name"
    else -> "$group:$name${if (version == null) "" else ":$version"}"
}
