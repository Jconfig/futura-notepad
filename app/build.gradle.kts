plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("org.jetbrains.kotlin.plugin.compose")
  id("com.github.triplet.play")
  kotlin("kapt")
}

android {
  namespace = "xyz.joydeb.futura_notepad"
  compileSdk = 35

  defaultConfig {
    applicationId = "xyz.joydeb.futura_notepad"
    minSdk = 24
    targetSdk = 35
    versionCode = 2
    versionName = "1.1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables.useSupportLibrary = true
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
      signingConfig = signingConfigs.findByName("release") ?: signingConfigs.getByName("debug")
    }
    debug {
      applicationIdSuffix = ".debug"
      versionNameSuffix = "-debug"
      isMinifyEnabled = false
    }
  }

  signingConfigs {
    create("release") {
      val storeFilePath = System.getenv("STORE_FILE")
      if (!storeFilePath.isNullOrBlank()) {
        storeFile = file(storeFilePath)
        storePassword = System.getenv("STORE_PASSWORD")
        keyAlias = System.getenv("KEY_ALIAS")
        keyPassword = System.getenv("KEY_PASSWORD")
      }
    }
  }

  buildFeatures { compose = true }

  packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }

  lint { warningsAsErrors = false; abortOnError = false }
}

play {
  val playJson = System.getenv("PLAY_SERVICE_JSON")
  if (!playJson.isNullOrBlank()) {
    serviceAccountCredentials.set(file(playJson))
  }
  track.set("internal")
  defaultToAppBundles.set(true)
}

dependencies {
  val composeBom = platform("androidx.compose:compose-bom:2024.10.01")
  implementation(composeBom)
  androidTestImplementation(composeBom)

  implementation("androidx.core:core-ktx:1.13.1")
  implementation("androidx.activity:activity-compose:1.9.3")
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-tooling-preview")
  implementation("androidx.compose.material3:material3:1.3.0")
  implementation("com.google.android.material:material:1.12.0")
  debugImplementation("androidx.compose.ui:ui-tooling")

  implementation("androidx.navigation:navigation-compose:2.8.3")

  implementation("androidx.room:room-ktx:2.7.0")
  implementation("androidx.room:room-runtime:2.7.0")
  kapt("androidx.room:room-compiler:2.7.0")
  implementation("androidx.room:room-paging:2.7.0")

  implementation("androidx.datastore:datastore-preferences:1.1.1")
  implementation("androidx.security:security-crypto:1.1.0-alpha06")
  implementation("com.google.mlkit:text-recognition:16.0.1")
  implementation("androidx.glance:glance-appwidget:1.1.0")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.2.1")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
