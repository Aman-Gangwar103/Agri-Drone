

plugins {


    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id ("kotlin-android")

    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")


    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")

    id("com.google.devtools.ksp")


}


android {
    namespace  = "com.example.socialmediaapp"
    compileSdk = 34

    defaultConfig {
        applicationId  = "com.example.socialmediaapp"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }


    buildFeatures {
        viewBinding =  true
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField ("boolean", "ENABLE_FEATURE", "true")
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility =  JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()

    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kapt {
        correctErrorTypes =  true
    }

}

dependencies {

    //AR
    implementation("com.google.ar:core:1.41.0")
    implementation("de.javagl:obj:0.4.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")







    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")



    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.firebase:firebase-database-ktx:20.3.0")
    implementation("com.google.firebase:firebase-messaging-ktx:23.4.0")
    implementation("junit:junit:4.12")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Material Design
    implementation("com.google.android.material:material:1.11.0")



    // ExoPlayer
    implementation("com.google.android.exoplayer:exoplayer:2.19.1")
    implementation("com.google.android.exoplayer:exoplayer-core:2.19.1")
    implementation("com.google.android.exoplayer:exoplayer-ui:2.19.1")




    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.49")
    kapt ("com.google.dagger:hilt-android-compiler:2.49")
    kapt ("androidx.hilt:hilt-compiler:1.1.0")



    // Lifecycle Components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.2")
    kapt("androidx.lifecycle:lifecycle-compiler:2.6.2")

    // Activity KTX for viewModels()
    implementation("androidx.activity:activity-ktx:1.8.2")

    // AdMob


    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    kapt("com.github.bumptech.glide:compiler:4.15.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Navigation Components
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    // Shimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")

    // Firebase

    implementation("com.google.firebase:firebase-analytics:21.5.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation(platform("com.google.firebase:firebase-bom:28.4.0"))
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.0")
    implementation("com.google.firebase:firebase-storage-ktx:20.3.0")


    implementation("de.hdodenhof:circleimageview:3.1.0")

    // SDP
    implementation("com.intuit.sdp:sdp-android:1.1.0")

    // Chip Navigation Bar
    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    // Fragment KTX
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // ReadMoreTextView
    implementation("com.borjabravo:readmoretextview:2.1.0")

    implementation ("com.google.android.gms:play-services-auth:19.2.0")

    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation ("androidx.activity:activity-compose:1.5.1")



    implementation ("androidx.compose.ui:ui:1.3.0-alpha02")
    implementation ("androidx.compose.material:material:1.3.0-alpha02")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.3.0-alpha02")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.3.0-alpha02")


    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.24.2-alpha")

    // required by google map compose
    implementation ("com.google.maps.android:maps-compose:2.1.1")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("androidx.compose.foundation:foundation:0.24.2-alpha")

    // required by get the last known location
    implementation ("com.google.android.gms:play-services-location:20.0.0")
    implementation ("com.google.maps.android:android-maps-utils:2.2.0")

    testImplementation ("junit:junit:4.13.2")




    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.20")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("com.google.code.gson:gson:2.8.6")

    implementation("com.google.mlkit:translate:17.0.2")
    implementation("com.google.mlkit:language-id:17.0.4")

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    implementation("com.pubnub:pubnub-gson:4.12.0")
}



