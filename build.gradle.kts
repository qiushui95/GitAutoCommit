plugins {
    id("application")
    id("java")
    kotlin("jvm") version "1.4.10"
}


group = "son.ysy"
version = "1.0.0"

repositories {
    mavenCentral()
}

application{
    mainClassName = "son.ysy.MainKt"
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("joda-time:joda-time:2.10.6")
    implementation("org.eclipse.jgit:org.eclipse.jgit:5.9.0.202009080501-r")
}
