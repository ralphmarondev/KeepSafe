import java.util.Properties

// Load config.properties from root
val configProperties = Properties().apply {
    val configFile = rootProject.file("config.properties")
    if (configFile.exists()) {
        load(configFile.inputStream())
        println("✅ Loaded config.properties")
    } else {
        // Create default placeholders (for CI or first run)
        put("FIREBASE_API_KEY", "\"MISSING_API_KEY_PLEASE_CREATE_CONFIG_PROPERTIES\"")
        put("FIREBASE_PROJECT_ID", "\"MISSING_PROJECT_ID_PLEASE_CREATE_CONFIG_PROPERTIES\"")
        println("⚠️  config.properties not found. Using placeholders.")
        println("   Create 'config.properties' with your Firebase credentials")
        println("   Or run: ./setup-config.sh")
    }
}

// Make properties available to all subprojects
allprojects {
    ext.set("firebaseApiKey", configProperties.getProperty("FIREBASE_API_KEY"))
    ext.set("firebaseProjectId", configProperties.getProperty("FIREBASE_PROJECT_ID"))
}

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
}
