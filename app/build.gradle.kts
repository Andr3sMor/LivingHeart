plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.cardiovascularmodel.livingheart"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cardiovascularmodel.livingheart2"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // BOM de Compose (para alinear versiones de Compose UI/Material3)
    implementation(platform(libs.androidx.compose.bom))

    // Módulos de Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // Optional - For collecting Flow<Preferences> in a lifecycle-aware manner
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0") // Or the latest version
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    // ... other dependencies

        // Import the BoM for the Firebase platform
        implementation(platform("com.google.firebase:firebase-bom:32.7.4")) // Check for the latest version

        // Add the dependency for the Firebase Authentication library
        // When using the BoM, you don't specify versions in Firebase library dependencies
        implementation("com.google.firebase:firebase-auth-ktx")

        // Add the dependency for the Cloud Firestore library
        implementation("com.google.firebase:firebase-firestore-ktx")

    // Navegación en Compose
    implementation("androidx.navigation:navigation-compose:2.9.0")

    // ─── Dependencias de Firebase ───────────────────────────────────────────────────────
    // A) Importamos el BOM de Firebase para alinear versiones de todas las librerías Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.1.0"))
    // B) Firebase Authentication (KTX)
    implementation("com.google.firebase:firebase-auth-ktx")
    // C) Coroutines Play Services para poder usar `await()` en llamadas de Firebase
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.1")
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // ─── Otras dependencias de testing ─────────────────────────────────────────────────
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // BOM para testing de Compose
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Para previsualización de composables en Android Studio
    debugImplementation(libs.androidx.ui.tooling)

    debugImplementation(libs.androidx.ui.test.manifest)
}
