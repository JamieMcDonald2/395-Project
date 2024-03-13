/**
 *  build.gradle.kts v1.05
 *  - added: testImplementation("org.mockito:mockito-core:3.12.4") for unit testing
 *           androidTestImplementation("androidx.compose.ui:ui-test-junit4:")
 *           androidTestImplementation("androidx.compose.ui:ui-test-manifest:")
 */

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.relay") version "0.3.11"
}

android {
    namespace = "com.example.cmpt395aurora"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.cmpt395aurora"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.9"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

/**trying to add testing*/
//allprojects {
//    repositories {
//        google() // For Google's Maven repository
//        jcenter() // For JCenter repository
//        mavenCentral() // For Maven Central repository
//        // Other repositories you might need
//    }
//}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.02.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.35.0-alpha")
//    implementation("androidx.wear.compose:compose-material:1.3.0")
    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("org.mockito.kotlin:mockito-kotlin:5.3.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.2")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation("org.mockito:mockito-core:3.12.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.2")
    debugImplementation("androidx.compose.ui:ui-tooling")
}