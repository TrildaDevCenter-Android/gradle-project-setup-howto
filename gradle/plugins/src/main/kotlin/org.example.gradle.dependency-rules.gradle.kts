plugins {
    id("org.gradlex.jvm-dependency-conflict-resolution")
}

jvmDependencyConflicts {
    // Configure logging capabilities plugin to default to Slf4JSimple
    logging {
        enforceSlf4JSimple()
    }

    patch {
        module("com.github.racc:typesafeconfig-guice") {
            // remove and re-add due to 'no_aop' classifier
            removeDependency("com.google.inject:guice")
            addApiDependency("com.google.inject:guice")
        }

        align(
            "org.apache.httpcomponents:httpclient",
            "org.apache.httpcomponents:httpmime",
            "org.apache.httpcomponents:fluent-hc",
            "org.apache.httpcomponents:httpclient-cache",
            "org.apache.httpcomponents:httpclient-win",
            "org.apache.httpcomponents:httpclient-osgi"
        )
        align(
            "org.apache.poi:poi",
            "org.apache.poi:poi-excelant",
            "org.apache.poi:poi-ooxml",
            "org.apache.poi:poi-scratchpad"
        )
        align(
            "org.slf4j:slf4j-api",
            "org.slf4j:slf4j-simple",
            "org.slf4j:slf4j-nop",
            "org.slf4j:slf4j-jdk14",
            "org.slf4j:slf4j-log4j12",
            "org.slf4j:slf4j-reload4j",
            "org.slf4j:slf4j-jcl",
            "org.slf4j:slf4j-android",
            "org.slf4j:slf4j-ext",
            "org.slf4j:jcl-over-slf4j",
            "org.slf4j:log4j-over-slf4j",
            "org.slf4j:jul-to-slf4j",
            "org.slf4j:osgi-over-slf4j"
        )
        align(
            "com.google.inject.extensions:guice-servlet",
            "com.google.inject:guice"
        )
        align(
            "org.jboss.resteasy:resteasy-core",
            "org.jboss.resteasy:resteasy-guice",
            "org.jboss.resteasy:resteasy-jackson2-provider"
        )
    }
}
