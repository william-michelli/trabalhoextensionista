plugins {
    alias(libs.plugins.android.application)
    //Serviços do Google para conexão com Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.aula.trabalhoextensionista"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aula.trabalhoextensionista"
        minSdk = 24
        targetSdk = 35
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Import Firebase BoM (Build of Materials)
    implementation(platform(libs.firebase.bom))
    // Pode adicionar dependências de outros módulos do firebase tb
    // Quando usando o BoM, nao especificar versões em dependências do Firebase
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)

    // Outras dependências firebase:
    // https://firebase.google.com/docs/android/setup#available-libraries

    //Para mostrar as tags
    implementation(libs.google.flexbox)

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
}