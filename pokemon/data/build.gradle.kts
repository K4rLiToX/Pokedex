plugins {
    alias(libs.plugins.pokedex.jvm.library)
    alias(libs.plugins.pokedex.jvm.hilt)
}

dependencies {
    api(projects.pokemon.domain)

    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}