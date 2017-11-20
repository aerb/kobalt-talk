import com.beust.kobalt.TaskResult
import com.beust.kobalt.api.Project
import com.beust.kobalt.api.annotation.Task
import com.beust.kobalt.plugin.application.application
import com.beust.kobalt.plugin.packaging.assemble
import com.beust.kobalt.project
import java.io.File

@Task(name = "createVersion", reverseDependsOn = arrayOf("compile"))
fun taskCreateVersion(project: Project): TaskResult {
    val src = File("src/main/kotlin/com/example").also { it.mkdirs() }
    val template = File("src/main/resources/VersionTemplate.txt")
        .readText()
        .replace("@VERSION@", project.version.toString())
    File(src, "Version.kt").writeText(template)
    return TaskResult()
}

val p = project {
    name = "kobalt"
    group = "com.example"
    artifactId = name
    version = "0.1"

    dependencies {
//        compile("com.beust:jcommander:1.68")
        compile("org.jetbrains.kotlin:kotlin-runtime:1.1.51")
        compile("org.jetbrains.kotlin:kotlin-stdlib:1.1.51")
    }

    dependenciesTest {
        compile("org.testng:testng:6.11")
    }

    assemble {
        jar {
        }
    }

    application {
        mainClass = "com.example.MainKt"
    }
}
