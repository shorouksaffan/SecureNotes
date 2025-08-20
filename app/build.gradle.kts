plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
    alias(libs.plugins.protobuf)
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.example.personalnotesapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.personalnotesapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }
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
        viewBinding = true
        compose = true
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.1" // use single latest
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") { option("lite") }
                create("kotlin") { option("lite") }
            }
        }
    }
}

dependencies {
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Hilt
    implementation(libs.hilt.android)

    // ✅ Proto DataStore (no duplicates)
    implementation("androidx.datastore:datastore:1.1.0")
    implementation("androidx.datastore:datastore-core:1.1.0")
    implementation("com.google.protobuf:protobuf-javalite:3.25.1")
    implementation("com.google.protobuf:protobuf-kotlin-lite:3.25.1")

    // ✅ Navigation (only keep these three)
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Material Design
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.4")
    // CardView
    implementation("androidx.cardview:cardview:1.0.0")
    implementation ("com.google.dagger:hilt-android:2.44")


    implementation ("androidx.hilt:hilt-navigation-fragment:1.0.0")
    ksp("com.google.dagger:hilt-compiler:2.44")
    // AndroidX / Compose
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Security
    implementation(libs.androidx.security.crypto)

    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter)
    testImplementation(kotlin("test"))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
