plugins {
    alias(libs.plugins.pokedex.jvm.library)
    alias(libs.plugins.ksp)
}

dependencies {
    api(projects.pokemon.domain)

    implementation(libs.hilt.core)
    implementation(libs.hilt.compiler)

    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}