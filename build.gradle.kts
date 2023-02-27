buildscript {
    dependencies {
        classpath("com.atradius.andromeda.sdk:openapi-generator-template:0.1.0-SNAPSHOT")
        classpath("nl.litpho.mybatis:mybatisgenerator-plugins:0.3.0")
    }
}

plugins {
    `java-library`
    id("nl.litpho.mybatisgenerator") version "0.1.0"
    id("org.openapi.generator") version("6.4.0")
    id("org.springframework.boot") version ("3.0.2")
}

java{
    sourceCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}

repositories {
    mavenLocal()
}

dependencies {
    liquibaseRuntime("com.h2database:h2:2.1.214")
    liquibaseRuntime("info.picocli:picocli:4.+")
    liquibaseRuntime("org.liquibase:liquibase-core:4.17.2")

    mybatisgeneratorRuntime("com.h2database:h2:2.1.214")

    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.0.2"))

    implementation("de.huxhorn.sulky:de.huxhorn.sulky.ulid:8.3.0")
    implementation("io.swagger.core.v3:swagger-annotations:2.2.8")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.1")
    implementation("nl.litpho.mybatis:mybatisgenerator-library:0.3.0")
    implementation("org.mybatis:mybatis:3.5.11")
    implementation("org.mybatis.dynamic-sql:mybatis-dynamic-sql:1.4.1")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    compileOnly("org.projectlombok:lombok:1.18.26")

    annotationProcessor("org.projectlombok:lombok:1.18.26")

    testImplementation("com.atradius.andromeda.sdk:andromeda-sdk-mybatis-library:0.1.0-SNAPSHOT")
    testImplementation("org.liquibase:liquibase-core")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")
    testImplementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testRuntimeOnly("com.h2database:h2:2.1.214")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

mybatisgenerator {
    configFile.set(file("$projectDir/src/main/mybatis/generatorConfig.xml"))
    database {
        connectionUrl.set("jdbc:h2:${projectDir}/build/db/h2")
        driverClass.set("org.h2.Driver")
        username.set("sa")
        password.set("")
    }
    directories {
        java.set(file("$buildDir/generatedSources/src/main/java"))
    }
    liquibase {
        changelogLocation.set(file("$projectDir/src/main/resources/db/changelog/changelog-master.xml"))
        logLevel.set(nl.litpho.mybatisgenerator.LiquibaseLogLevel.FINE)
    }
}

openApiGenerate {
    generatorName.set("atradius-spring")
    inputSpec.set("$rootDir/src/main/specs/api_spec.yml")
    outputDir.set("$buildDir/generatedSources")
    apiPackage.set("nl.litpho.mybatis.openapi")
    modelPackage.set("nl.litpho.mybatis.openapi.model")
    modelNameSuffix.set("Dto")
    configOptions.set(mapOf(
        "dateLibrary" to "java8-localdatetime",
        "implicitHeaders" to "true",
        "openApiNullable" to "false",
        "useBeanValidation" to "true",
        "useSpringBoot3" to "true",
        "useTags" to "true"
    ))
}

sourceSets {
    main {
        java {
            srcDir("$buildDir/generatedSources/src/main/java")
        }
    }
}

tasks.withType<JavaCompile> {
    dependsOn("generate")
}

tasks.withType<ProcessResources> {
    dependsOn("generate")
}

tasks.named("compileJava") {
    dependsOn("openApiGenerate")
}

tasks.named("processResources") {
    dependsOn("openApiGenerate")
}
