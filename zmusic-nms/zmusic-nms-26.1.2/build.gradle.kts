plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.24"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

java {
    // Paper 26.x 的类文件为 Java 25，需要 JDK 25 才能读取
    toolchain.languageVersion = JavaLanguageVersion.of(25)
    // 产物目标低于开发包声明的 Java 25，解析依赖时不按 JVM 版本筛选
    disableAutoTargetJvm()
}

tasks.withType<JavaCompile>().configureEach {
    // 产物保持 Java 21：插件在 1.20.5~1.21.x Paper 上加载时会被整体重映射，过新的类文件可能无法解析
    options.release = 21
}

dependencies {
    paperweight.paperDevBundle("26.1.2.build.74-stable")
    compileOnly("net.kyori:adventure-text-serializer-gson:4.23.0")
    compileOnly(project(":zmusic-nms:zmusic-nms-core"))
}
