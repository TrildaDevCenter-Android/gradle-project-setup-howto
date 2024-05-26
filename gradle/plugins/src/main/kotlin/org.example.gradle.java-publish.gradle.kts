plugins {
    id("java")
    id("maven-publish")
}

publishing.publications.create<MavenPublication>("mavenJava") {
    from(components["java"])

    // We use consistent resolution + a platform for controlling versions
    // -> Publish the versions that are the result of the consistent resolution
    versionMapping {
        allVariants { fromResolutionResult() }
    }
}

// The repository to publish to
publishing.repositories {
    maven("/tmp/my-repo")
}
