plugins {
    id("org.example.gradle.platform")
}

dependencies.constraints {
    api("com.fasterxml.jackson.core:jackson-core:2.13.5")
    api("com.github.racc:typesafeconfig-guice:0.1.0")
    api("com.google.inject:guice:5.1.0")
    api("com.sun.activation:jakarta.activation:1.2.2") { version { reject("[2.0.0,)") } } // Upgrade to 2.x requires newer Jakarta APIs
    api("com.sun.mail:jakarta.mail:1.6.7") { version { reject("[2.0.0,)") } } // Upgrade to 2.x requires newer Jakarta APIs
    api("jakarta.inject:jakarta.inject-api:1.0.5") { version { reject("[2.0.0,)") } } // Upgrade to 2.x requires newer Jakarta APIs
    api("jakarta.servlet:jakarta.servlet-api:4.0.4") { version { reject("[5.0.0,)") } } // Stay Tomcat 8 compatible
    api("org.apache.commons:commons-lang3:3.9")
    api("org.apache.httpcomponents:httpclient:4.5.13")
    api("org.apache.poi:poi:5.2.2")
    api("org.apache.solr:solr-solrj:7.7.3") { version { reject("[8.0.0,)") } } // API changes in 8 require production code changes
    api("org.apache.velocity:velocity-engine-core:2.3")
    api("org.apache.zookeeper:zookeeper:3.8.0")
    api("org.assertj:assertj-core:3.22.0")
    api("org.jboss.resteasy:resteasy-core:4.7.6.Final")  { version { reject("[5.0.0.Final,)") } }
    api("org.junit.jupiter:junit-jupiter-api:5.7.2")
    api("org.mockito:mockito-core:4.5.1")
    api("org.opensaml:opensaml:2.6.4")
    api("org.reflections:reflections:0.9.11") { version { reject("[0.9.12,)") } } // Upgrade breaks 'com.github.racc:typesafeconfig-guice'
    api("org.slf4j:slf4j-api:1.7.36")
}
