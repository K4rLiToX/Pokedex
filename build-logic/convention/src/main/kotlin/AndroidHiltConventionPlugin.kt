import com.carlosdiestro.pokedex.implementation
import com.carlosdiestro.pokedex.ksp
import com.carlosdiestro.pokedex.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidHiltConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.dagger.hilt.android")
                apply("com.google.devtools.ksp")
            }

            dependencies {
                implementation(libs.findLibrary("hilt-android").get())
                ksp(libs.findLibrary("hilt-compiler").get())
            }
        }
    }
}