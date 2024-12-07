plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.calberto_barbosa_jr.firebasehub"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.calberto_barbosa_jr.firebasehub"
        minSdk = 24
        targetSdk = 34
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

    viewBinding { enable = true }
    testOptions {
        unitTests {
            isReturnDefaultValues = true // Ignore non-mocked methods
            isIncludeAndroidResources = true // Includes Android features for unit testing
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Injeção de dependência
    implementation ("io.insert-koin:koin-android:3.4.0")
    implementation ("io.insert-koin:koin-android-compat:3.4.0")


    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation ("com.google.android.gms:play-services-auth:21.2.0")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("com.google.firebase:firebase-database-ktx")
    implementation ("com.google.firebase:firebase-storage-ktx")
    implementation ("com.google.firebase:firebase-firestore-ktx")

    // Glide
    //https://github.com/bumptech/glide
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")

    implementation ("io.gitlab.alexto9090:materialsearchview:1.0.0")

    //=========================================================================
    // Tests

    // Mockito
    testImplementation ("org.mockito:mockito-core:3.11.2")
    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation ("com.nhaarman.mockitokotlin2:mockito-kotlin:2.0.0-alpha03@jar")
    // https://github.com/mockito/mockito-kotlin
    testImplementation ("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation ("org.mockito:mockito-inline:3.11.2")

    //testImplementation ("androidx.arch.core:core-testing:2.2.0")
    testImplementation ("androidx.arch.core:core-testing:2.1.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    //robolectric
    testImplementation ("org.robolectric:robolectric:4.10")

    implementation ("androidx.test:core:1.5.0")
    implementation ("androidx.test:rules:1.4.0")
    implementation ("androidx.test:runner:1.4.0")
    //implementation ("com.google.firebase:firebase-common")

    //implementation ("com.google.truth:truth:1.0.1")

    /*
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation ("androidx.compose.ui:ui:1.5.0")
    implementation ("androidx.compose.material3:material3:1.2.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

     */

    testImplementation ("io.insert-koin:koin-test:4.0.0")
    testImplementation ("io.insert-koin:koin-test-junit4:4.0.0")
}