buildscript {
    dependencies {
        classpath(libs.google.services)
    }
}
plugins {
    id("com.android.application") version "8.4.0" apply false
    id("com.android.library") version "8.0.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}
