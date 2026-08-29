import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties: Properties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}

buildConfig {
    packageName("com.ralphmarondev.keepsafe")

    buildConfigField(
        "String",
        "FIREBASE_API_KEY",
        "\"${localProperties.getProperty("FIREBASE_API_KEY", "")}\""
    )
    buildConfigField(
        "String",
        "FIREBASE_APP_ID",
        "\"${localProperties.getProperty("FIREBASE_APP_ID", "")}\""
    )
    buildConfigField(
        "String",
        "FIREBASE_PROJECT_ID",
        "\"${localProperties.getProperty("FIREBASE_PROJECT_ID", "")}\""
    )
}

dependencies {
    implementation(project(":shared"))

    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)
    implementation(libs.bundles.koin)
}

compose.desktop {
    application {
        mainClass = "com.ralphmarondev.keepsafe.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.ralphmarondev.keepsafe"
            packageVersion = "1.0.0"
        }
    }
}