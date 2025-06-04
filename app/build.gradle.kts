plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.cardiovascularmodel.livingheart"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cardiovascularmodel.livingheart"
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
    implementation("io.coil-kt:coil-compose:2.5.0")
    implementation(platform("com.google.firebase:firebase-bom:33.1.2")) // Usa la última versión del BoM
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx") // Si usas Realtime Database para métricas
    implementation(platform("com.google.firebase:firebase-bom:33.0.0")) // Check for the latest BoM version
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")

    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.compose.material:material-icons-core:1.7.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.0") // Para más iconos
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")
    implementation("androidx.navigation:navigation-compose:2.9.0") // Para la navegación dentro del dashboard si es necesario
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.ui:ui:...") // Replace ... with the latest version
    implementation("androidx.compose.material3:material3:...") // Or material, if you're using Material 2
    implementation("androidx.compose.ui:ui-tooling-preview:...")
    implementation(platform("com.google.firebase:firebase-bom:33.1.2")) // Usa la BoM para manejar versiones
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3") // Para usar .await() con Tasks de Firebase
    // A) Importamos el BOM de Firebase para alinear versiones de todas las librerías Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.1.0"))
    // B) Firebase Authentication (KTX)
    implementation("com.google.firebase:firebase-auth-ktx")
    // C) Coroutines Play Services para poder usar await() en llamadas de Firebase
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1") // Para integrar con Firebase Tasks
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.navigation:navigation-compose:2.9.0")
    implementation(libs.firebase.auth.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}