plugins {
    alias(libs.plugins.pokedex.jvm.library)
}

dependencies {
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}