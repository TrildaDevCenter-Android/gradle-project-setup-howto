plugins {
    id("org.cyclonedx.bom")
}

tasks.cyclonedxBom {
    notCompatibleWithConfigurationCache("https://github.com/CycloneDX/cyclonedx-gradle-plugin/issues/193")
    includeConfigs.add(JavaPlugin.RUNTIME_CLASSPATH_CONFIGURATION_NAME)
}
