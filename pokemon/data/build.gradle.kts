plugins {
    alias(libs.plugins.pokedex.jvm.library)
}

dependencies {
    api(projects.pokemon.domain)

    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}