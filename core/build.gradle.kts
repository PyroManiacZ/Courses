plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization")         // kotlinx.serialization
    id("com.google.devtools.ksp")
    alias(libs.plugins.kotlin.compose)  // KSP для Room
}

android {
    namespace = "ru.keckinnd.core"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    // --- Network ---
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.kotlinx.coroutines.core)

    // --- Database (Room + KSP) ---
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // --- UI (Compose) ---
    // BOM для управления версиями Compose
    implementation(platform(libs.androidx.compose.bom.v20230501))
    implementation(libs.androidx.compose.ui.ui)            // compose-ui
    implementation(libs.androidx.compose.material3.material3) // material3
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.ui.tooling)

    // --- DI (Koin) ---
    implementation(libs.koin.core)
    implementation(libs.koin.android)
}
