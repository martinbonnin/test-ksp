plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.google.devtools.ksp")
}

kotlin {
    jvm()
    iosArm64()
}

dependencies {
    add("kspJvm", project(":processor"))
}

tasks.register("run", JavaExec::class.java) {
    this.classpath(tasks.named("jvmJar"))
    this.classpath(configurations.getByName("jvmRuntimeClasspath"))
    mainClass.set("app.MainKt")
}