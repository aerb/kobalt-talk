build-lists: true
#Kobalt!

---

#What is it?

- kotlin build DSL, simlar to gradle.
- static and supports auto-complete.
- incremental tasks.
- easy custom task addition.
- parallel builds.
- clean command-line interface, and excellent IntelliJ integration.

---

# Getting started with Kobalt

- `brew install kobalt`
- `mvn -B archetype:generate \
      -DarchetypeGroupId=org.apache.maven.archetypes \
      -DgroupId=com.mycompany.app \
      -DartifactId=my-app`
- `gradle init --type java-library`
- `kobaltw --init kotlin`

---

# The Result

```
├── kobalt
│   ├── src
│   │   └── Build.kt
│   └── wrapper
│       ├── kobalt-wrapper.jar
│       └── kobalt-wrapper.properties
├── kobaltw
├── kobaltw.bat
├── src
│   ├── main
│   │   └── kotlin
│   │       └── com
│   │           └── example
│   │               └── Main.kt
│   └── test
│       └── kotlin
│           └── com
│               └── example
│                   └── MainTest.kt
└── talk.md
```

---

# Build.kt

```
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
```

---

# Custom Tasks!

```kotlin
@Task(name = "createVersion", reverseDependsOn = arrayOf("compile"))
fun taskCreateVersion(project: Project): TaskResult {
    val src = File("src/main/kotlin/com/example").also { it.mkdirs() }
    val template = File("src/main/resources/VersionTemplate.txt")
        .readText()
        .replace("@VERSION@", project.version.toString())
    File(src, "Version.kt").writeText(template)
    return TaskResult()
}
```

---

# Fat Jars!!!

- first class plugin in kobalt.
- great for portable kotlin scripts.
- awkward in mavnen / gradle

```
 jar {
    fatJar = true
    manifest {
        attributes("Main-Class", "com.beust.FileHavingMainKt")
    }
}
```

---

# Publishing

```
pom = Model().apply {
    name = project.name
    description = "A build system in Kotlin"
    url = "http://beust.com/kobalt"
    licenses = listOf(License().apply {
        name = "Apache 2.0"
        url = "http://www.apache .org/licenses/LICENSE-2.0"
    })
    scm = Scm().apply {
        url = "http://github.com/cbeust/kobalt"
        connection = "https://github.com/cbeust/kobalt.git"
        developerConnection = "git@github.com:cbeust/kobalt.git"
    }
    developers = listOf(Developer().apply {
        name = "Cedric Beust"
        email = "cedric@beust.com"
    })
}
```
