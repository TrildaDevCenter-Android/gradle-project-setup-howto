plugins {
    id("base")
    id("com.autonomousapps.dependency-analysis")
}

// Configure the 'tasks' task such that when you run './gradlew' without arguments you see only the tasks of the
// 'build' group, which are the tasks for daily development.
defaultTasks("tasks")
tasks.named<TaskReportTask>("tasks") { displayGroup = "build" }

// Configure the dependency analysis plugin to fail if issues are found (when running 'qualityCheck')
dependencyAnalysis { issues { all { onAny { severity("fail") } } } }
