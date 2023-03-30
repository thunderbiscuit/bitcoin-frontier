plugins {
    id("java-gradle-plugin")
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        create("uniFfiAndroidBindings") {
            id = "com.tb.plugins.generate-android-bindings"
            implementationClass = "com.tb.plugins.UniFfiAndroidPlugin"
        }
    }
}
