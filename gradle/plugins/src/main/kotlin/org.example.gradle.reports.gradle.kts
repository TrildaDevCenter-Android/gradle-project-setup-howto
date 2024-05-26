plugins {
    id("jacoco-report-aggregation") // get and aggregated coverage report for all tests
    id("test-report-aggregation")   // get and aggregated result report for all tests
    id("org.example.gradle.java")
}

configurations.aggregateTestReportResults {
    shouldResolveConsistentlyWith(configurations["mainRuntimeClasspath"])
}
configurations.aggregateCodeCoverageReportResults {
    shouldResolveConsistentlyWith(configurations["mainRuntimeClasspath"])
}
tasks.check {
    dependsOn(tasks.testAggregateTestReport)
    dependsOn(tasks.testCodeCoverageReport)
}
