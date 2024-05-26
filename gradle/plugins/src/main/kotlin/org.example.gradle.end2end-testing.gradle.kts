plugins {
    id("java")
}

// Specific API fixtures used for testing without live service
val mockApi = sourceSets.create("mockApi")
java.registerFeature(mockApi.name) { usingSourceSet(mockApi) }
tasks.named("mockApiJar") { group = null }

// end-to-end tests located in the :app project
testing.suites.create<JvmTestSuite>("endToEndTest") {
    useJUnitJupiter("")
    targets.named("endToEndTest") {
        testTask {
            group = "build"
            options {
                this as JUnitPlatformOptions
                excludeTags("slow")
            }
        }
        tasks.check { dependsOn(testTask) }
    }
    // Add a second task for the endToEndTest suite
    targets.register("endToEndTestSlow") {
        testTask {
            group = "build"
            options {
                this as JUnitPlatformOptions
                includeTags("slow")
            }
        }
    }
}
