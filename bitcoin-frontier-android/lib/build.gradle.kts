plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android") version "1.6.10"
    id("maven-publish")
    id("signing")

    // Custom plugin to generate the native libs and bindings file
    id("com.tb.plugins.generate-android-bindings")
}

repositories {
    mavenCentral()
    google()
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(file("proguard-android-optimize.txt"), file("proguard-rules.pro"))
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation("net.java.dev.jna:jna:5.12.1@aar")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("androidx.core:core-ktx:1.7.0")
    api("org.slf4j:slf4j-api:1.7.30")

    // Use the Kotlin test library
    testImplementation(kotlin("test"))
    testImplementation("net.java.dev.jna:jna:5.12.1@aar")
    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.8.2")

    // androidTestImplementation("com.github.tony19:logback-android:2.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    // androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "com.tb"
                artifactId = "bitcoin-frontier-android"
                version = "0.1.0"

                from(components["release"])
                pom {
                    name.set("bitcoin-frontier-android")
                    description.set("Bitcoin frontier Android library; bindings for experimental Rust bitcoin libraries.")
                    url.set("")
                    licenses {
                        license {
                            name.set("APACHE 2.0")
                            url.set("")
                        }
                    }
                    // developers {
                    //     developer {
                    //         id.set("")
                    //         name.set("")
                    //         email.set("")
                    //     }
                    // }
                    // scm {
                    //     connection.set("")
                    //     developerConnection.set("")
                    //     url.set("")
                    // }
                }
            }
        }
    }
}

// signing {
//     val signingKeyId: String? by project
//     val signingKey: String? by project
//     val signingPassword: String? by project
//     useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
//     sign(publishing.publications)
// }
