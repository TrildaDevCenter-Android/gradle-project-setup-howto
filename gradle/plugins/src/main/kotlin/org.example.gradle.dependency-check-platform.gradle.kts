import org.example.gradle.tasks.DependencyFormatCheck
import org.example.gradle.tasks.PlatformVersionConsistencyCheck

plugins {
    id("java-platform")
}

val checkDependencyFormatting = tasks.register<DependencyFormatCheck>("checkDependencyFormatting") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP

    buildFilePath = project.buildFile.absolutePath
    shouldNotHaveVersions = false
    declaredDependencies.put("api", provider { configurations.api.get().dependencies.map { d -> d.toDeclaredString() } })
    declaredDependencies.put("runtime", provider { configurations.runtime.get().dependencies.map { d -> d.toDeclaredString() } })
    declaredConstraints.put("api", provider { configurations.api.get().dependencyConstraints.map { d -> d.toDeclaredString() } })
    declaredConstraints.put("runtime", provider { configurations.runtime.get().dependencyConstraints.map { d -> d.toDeclaredString() } })
}

// Install a task that checks the consistency of the platform against the resolution result of the product
val product = configurations.dependencyScope("product").get()
dependencies {
    product(platform(project(path)))
}
val purePlatformVersions = configurations.dependencyScope("purePlatformVersions") {
    withDependencies {
        add(project.dependencies.platform(project(project.path)))
        // Create a dependency for eac constraint defined in the platform (this is to check for unused entries)
        configurations.api.get().dependencyConstraints.forEach { constraint ->
            add(project.dependencies.create("${constraint.group}:${constraint.name}") { isTransitive = false })
        }
    }
}
val fullProductRuntimeClasspath = configurations.resolvable("fullProductRuntimeClasspath") {
    attributes.attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
    attributes.attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
    extendsFrom(product)
}
val purePlatformVersionsPath = configurations.resolvable("purePlatformVersionsPath") {
    attributes.attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
    attributes.attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
    extendsFrom(purePlatformVersions.get())
}
val checkPlatformVersionConsistency= tasks.register<PlatformVersionConsistencyCheck>("checkPlatformVersionConsistency") {
    group = HelpTasksPlugin.HELP_GROUP
    productClasspath = fullProductRuntimeClasspath.map { it.incoming.resolutionResult.allComponents }
    classpathFromPlatform = purePlatformVersionsPath.map { it.incoming.resolutionResult.allComponents }
}

tasks.named("qualityCheck") {
    dependsOn(checkDependencyFormatting)
    // TODO check/repair: dependsOn(checkPlatformVersionConsistency)
}

fun Dependency.toDeclaredString() = "$group:$name:$version"
fun DependencyConstraint.toDeclaredString() = "$group:$name:$version"
