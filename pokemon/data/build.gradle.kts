plugins {
    alias(libs.plugins.pokedex.jvm.library)
}

dependencies {
    implementation(projects.pokemon.domain)

    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}