import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    id ("dev.icerock.mobile.multiplatform-resources")
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        val desktopMain by getting

        val iosX64Main by getting {
            resources.srcDirs("build/generated/moko/iosX64Main/src")
        }
        val iosArm64Main by getting {
            resources.srcDirs("build/generated/moko/iosArm64Main/src")
        }
        val iosSimulatorArm64Main by getting {
            resources.srcDirs("build/generated/moko/iosSimulatorArm64Main/src")
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.engine.android)
            implementation(libs.decompose)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.materialIconsExtended)
            implementation(compose.material3)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.coil.compose)
            implementation(libs.napier)
            implementation(libs.coil.network)
            implementation(libs.decompose)
            implementation(libs.decompose.jetbrains)
            implementation(libs.zoomable)
        }

        iosMain.dependencies {
            implementation(libs.ktor.engine.ios)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "org.moneyking.imagepicker"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
            resources.srcDirs("src/commonMain/resources")
            java.srcDirs("build/generated/moko/androidMain/src")
        }
    }

    defaultConfig {
        applicationId = "org.moneyking.imagepicker"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
        implementation(libs.coil.compose)
        commonMainApi(libs.bundles.moko.resources)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.moneyking.imagepicker"
            packageVersion = "1.0.0"
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "org.moneyking.imagepicker"
    iosBaseLocalizationRegion = "ko" // default "en"
}

task("testClasses").doLast {
    println("This is a dummy testClasses task")
}
