plugins {

    kotlin("kapt")
    alias(libs.plugins.compose.compiler)
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.jayashree.countriesinfo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.jayashree.countriesinfo"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15" // Use the appropriate version
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isShrinkResources = false // If you're using this
            // If you have specific proguard files for debug, comment them out
            // proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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
}

dependencies {

    implementation(libs.androidx.core.ktx) // Ensure this is a recent stable version
    implementation(libs.androidx.appcompat) // Ensure this is a recent stable version
    implementation(libs.material) // For Material Components (View system) - remove if not using XML Material views
    implementation(libs.androidx.constraintlayout) // If using ConstraintLayout in XML

    // Activity & Lifecycle (Compose specific versions are often better if primarily Compose)
    implementation("androidx.activity:activity-compose:1.9.0") // Or latest stable
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5") // Or latest stable

    // Compose
    // Use a STABLE Compose BOM
    val composeBom = platform("androidx.compose:compose-bom:2024.05.00") // Check for the latest stable
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    // implementation("androidx.compose.runtime:runtime-livedata") // If using LiveData with Compose
    // implementation("androidx.compose.runtime:runtime-rxjava2") // If using RxJava2 with Compose

    // Networking
    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Consider updating to 2.11.0 or latest
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Consider updating
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0") // Or latest (4.x is fine, 5.x has breaking changes)

    // Image Loading
    implementation("com.github.bumptech.glide:glide:4.16.0") // Or latest stable for Glide
    kapt("com.github.bumptech.glide:compiler:4.16.0")
    implementation("io.coil-kt:coil-compose:2.7.0") // Coil for Compose

    // RecyclerView (if still needed for XML layouts)
    // implementation("androidx.recyclerview:recyclerview:1.3.2") // Or latest stable

    // Unit Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.12.0") // Or latest stable
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1") // Or latest stable, align with your coroutines version

    // Android Instrumented Testing
    androidTestImplementation("androidx.test.ext:junit-ktx:1.2.1") // Or latest stable (ktx version)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1") // Or latest stable
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.1") // Or latest stable
    androidTestImplementation("androidx.test:rules:1.6.1") // Or latest stable
    androidTestImplementation("androidx.compose.ui:ui-test-junit4") // From BOM
    debugImplementation("androidx.compose.ui:ui-test-manifest") // From BOM


}