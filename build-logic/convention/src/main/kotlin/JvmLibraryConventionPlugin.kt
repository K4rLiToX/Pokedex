import com.carlosdiestro.pokedex.configureKotlinJvm
import com.carlosdiestro.pokedex.implementation
import com.carlosdiestro.pokedex.libs
import com.carlosdiestro.pokedex.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            dependencies {
                implementation(libs.findLibrary("javax-inject").get())
                implementation(libs.findLibrary("kotlinx-coroutines-core").get())
                implementation(libs.findLibrary("kotlinx-coroutines-android").get())

                testImplementation(libs.findLibrary("junit").get())
            }

            configureKotlinJvm()
        }
    }
}