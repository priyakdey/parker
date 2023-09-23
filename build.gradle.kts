import org.gradle.api.JavaVersion.VERSION_21
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.util.*

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * To learn more about Gradle by exploring our Samples at https://docs.gradle.org/8.3/samples
 */
plugins {
    id("java")
    id("application")

    id("jacoco")
}

group = "com.priyakdey"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = VERSION_21
    targetCompatibility = VERSION_21
}

repositories {
    mavenCentral()
}

// Load dependency versions from external properties file
val dependenciesPropertiesFile: File = project.rootProject.file("dependencies.properties")
if (dependenciesPropertiesFile.exists()) {
    logger.lifecycle("Loading properties from $dependenciesPropertiesFile...")
    val properties = Properties()
    try {
        dependenciesPropertiesFile.reader().use { reader ->
            properties.load(reader)
        }

        properties.forEach { key, value ->
            project.extensions.extraProperties.set(key.toString(), value)
        }

    } catch (e: Exception) {
        logger.warn("An error occurred while loading properties from $dependenciesPropertiesFile", e)
    }
}

val junit5Version: String by project

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit5Version")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit5Version")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junit5Version")
}

application {
    mainClass = "com.priyakdey.parker.ParkerApplication"
}

// @formatter:off
tasks.withType<Test> {
    useJUnitPlatform()

    testLogging {
        lifecycle {
            events = mutableSetOf(TestLogEvent.STARTED, TestLogEvent.SKIPPED, TestLogEvent.FAILED)

            showExceptions = true
            showCauses = true
            showStackTraces = true

            info.events = lifecycle.events
            info.exceptionFormat = lifecycle.exceptionFormat
        }
    }

    // global state to store the test results
    val successTests: MutableList<TestDescriptor> = mutableListOf()
    val skippedTests: MutableList<TestDescriptor> = mutableListOf()
    val failedTests: MutableList<TestDescriptor>  = mutableListOf()

    // ansi color codes
    val RESET  = "\u001B[0m"
    val RED    = "\u001B[31m"
    val GREEN  = "\u001B[32m"
    val YELLOW = "\u001B[33m"

    // constants for pprint formatter
    val CLASSNAME_COL_WIDTH = 30
    val TESTNAME_COL_WIDTH  = 70
    val STATUS_COL_WIDTH    = 10

    val TABLE_BORDER = "+" + ("-".repeat(CLASSNAME_COL_WIDTH)) + "+" + ("-".repeat(TESTNAME_COL_WIDTH)) + "+" + ("-".repeat(STATUS_COL_WIDTH)) + "+"
    val TABLE_HEADER = "|       Class        |                           Test Name                                  |  Status  |"

    addTestListener(object : TestListener {
        override fun beforeSuite(suite: TestDescriptor?) {}

        override fun beforeTest(testDescriptor: TestDescriptor?) {}

        override fun afterTest(testDescriptor: TestDescriptor?, result: TestResult?) {
            when (result?.resultType) {
                TestResult.ResultType.SUCCESS -> testDescriptor?.let { successTests.add(it) }
                TestResult.ResultType.SKIPPED -> testDescriptor?.let { skippedTests.add(it) }
                TestResult.ResultType.FAILURE -> testDescriptor?.let { failedTests.add(it) }
                null -> assert(true) { "Unreachable" }
            }
        }

        override fun afterSuite(suite: TestDescriptor?, result: TestResult?) {
            if (suite?.parent == null) {
                logger.lifecycle("\n############################### Test Run Summary ###############################\n")
                logTestResultsTable()
                logStats()
                logger.lifecycle("\n################################################################################\n")
            }
        }


        private fun logTestResultsTable() {
            logger.lifecycle(TABLE_BORDER)
            logger.lifecycle(TABLE_HEADER)
            logger.lifecycle(TABLE_BORDER)

            successTests.forEach {
                logTestResult(it, "SUCCESS", GREEN)
            }

            skippedTests.forEach {
                logTestResult(it, "SKIPPED", YELLOW)
            }

            failedTests.forEach {
                logTestResult(it, "FAILED", RED)
            }

            logger.lifecycle(TABLE_BORDER)
        }

        private fun logTestResult(testDescriptor: TestDescriptor?, result: String?, color: String) {
            val parentName = testDescriptor?.parent?.displayName
            val testName = testDescriptor?.displayName
            println("|${center(parentName, CLASSNAME_COL_WIDTH)}|${center(testName, TESTNAME_COL_WIDTH)}|${color}${center(result, STATUS_COL_WIDTH)}$RESET|")
        }

        private fun center(s: String?, maxWidth: Int): String {
            if (s!!.length > maxWidth) {
                return s.substring(0, maxWidth)
            }

            val leftPadding = (maxWidth - s.length) / 2
            return s.padStart(s.length + leftPadding).padEnd(maxWidth)
        }

        private fun logStats() {
            val successCount = successTests.size
            val skippedCount = skippedTests.size
            val failedCount  = failedTests.size
            val totalCount   = successCount + skippedCount + failedCount

            logger.lifecycle("\nTest run statistics")
            logger.lifecycle("------------------------")

            println("Total  : $totalCount")
            println("Success: $successCount  (${"%.2f".format((successCount.toDouble() / totalCount) * 100)}%)")
            println("Skipped: $skippedCount  (${"%.2f".format((skippedCount.toDouble() / totalCount) * 100)}%)")
            println("Failed : $failedCount  (${"%.2f".format((failedCount.toDouble() / totalCount) * 100)}%)")

            logger.lifecycle("------------------------")
        }
    })

    finalizedBy(tasks.jacocoTestReport)

    configure<JacocoTaskExtension> {
        isEnabled = true
        excludes = listOf("com.priyakdey.parker.core.model.*")
    }

}
// @formatter:on

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
}