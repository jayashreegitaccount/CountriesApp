// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    //kotlin("android") version "2.0.0" // Use the appropriate Kotlin version

    id("com.android.application") version "8.2.1" apply false
    id("com.android.library") version "8.2.1" apply false
    kotlin("android") version "1.9.22" apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.google.gms.google.services) apply false

}
