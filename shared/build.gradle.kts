import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "com.ralphmarondev.keepsafe.shared"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
        }
    }

    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.gitlive.firebase.app)
            api(libs.gitlive.firebase.auth)
            api(libs.gitlive.firebase.firestore)
            api(libs.koin.core)

            api(libs.navigation.compose)
            api(libs.androidx.lifecycle.viewmodelCompose)
            api(libs.androidx.lifecycle.runtimeCompose)

            api(libs.compose.runtime)
            api(libs.compose.foundation)
            api(libs.compose.material3)
            api(libs.compose.ui)
            api(libs.compose.components.resources)
            api(libs.material.icons.extended)

            api(libs.datastore)
            api(libs.datastore.preferences)

            api(libs.bundles.coil)
        }
        androidMain.dependencies {
            api(project.dependencies.platform(libs.firebase.bom))
        }
    }
}