plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "de.osca.android.district"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-proguard-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        kotlin {
            kotlinOptions {
                // Nessesary for @OptIn Annotations to use Experimental Features
                freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
            }
        }
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val compose_version = "1.7.8"
val compose_navigation_version = "2.8.9"
val hilt_version = "2.56"
val kotlin_coroutines_version = "1.9.0"
val accompanist_version = "0.34.0"
val okhttp3_version = "4.12.0"
val room_version = "2.6.1"
val parse_version = "4.3.0"

dependencies {
    // NEEDED FOR SHARED PARSE OBJECT
    implementation(project(":oscaessentials-android"))
    // Android
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")

    // Android Compose
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.compose.ui:ui:$compose_version")
    implementation("androidx.compose.ui:ui-tooling:$compose_version")
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation("androidx.compose.foundation:foundation:$compose_version")
    implementation("androidx.compose.material3:material3:1.3.1")

    // Android Navigation
    implementation("androidx.navigation:navigation-ui-ktx:$compose_navigation_version")
    implementation("androidx.navigation:navigation-compose:$compose_navigation_version")

    // Android Livecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.8.7")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.coil-kt:coil-svg:2.2.2")
    implementation("com.google.firebase:protolite-well-known-types:18.0.0")

    // Android Hilt
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.dagger:hilt-android:$hilt_version")

    // Android Paging
    implementation("androidx.paging:paging-runtime-ktx:3.3.6")
    implementation("androidx.paging:paging-compose:3.3.6")

    // Images
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("io.coil-kt.coil3:coil-svg:3.0.1")

    // OkHttp
    implementation("com.squareup.okhttp3:okhttp:$okhttp3_version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.10.0")
    implementation("com.squareup.retrofit2:converter-kotlinx-serialization:2.11.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlin_coroutines_version")

    // Date / Time
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0-RC.2")

    // Google Maps
    implementation("com.google.maps.android:maps-compose:5.0.3")
    implementation("com.google.android.gms:play-services-maps:19.1.0")

    // GeoJson
    implementation("org.locationtech.jts:jts-core:1.20.0")

    // Accompanist
    implementation("com.google.accompanist:accompanist-permissions:0.37.2")

    // Parse
    implementation("com.github.parse-community.Parse-SDK-Android:parse:$parse_version")
    implementation("com.github.parse-community.Parse-SDK-Android:ktx:$parse_version")
    implementation("com.github.parse-community.Parse-SDK-Android:coroutines:$parse_version")

    // Documentation
    dokkaPlugin("org.jetbrains.dokka:android-documentation-plugin:1.9.20")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    testImplementation("org.mockito:mockito-all:1.10.19")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlin_coroutines_version")
    testImplementation("com.squareup.okhttp3:mockwebserver:$okhttp3_version")
    testImplementation("com.google.dagger:hilt-android-testing:$hilt_version")
    kaptTest("com.google.dagger:hilt-android-compiler:$hilt_version")
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hilt_version")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:$hilt_version")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.1.3")

    // Markdown
    implementation("com.halilibo.compose-richtext:richtext-markdown:1.0.0-alpha01")
    implementation("com.halilibo.compose-richtext:richtext-commonmark:1.0.0-alpha01")

    // ROOM
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    implementation("androidx.room:room-paging:$room_version")
}
