import com.carlosdiestro.pokedex.implementation
import com.carlosdiestro.pokedex.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("pokedex.android.library")
                apply("pokedex.android.hilt")
            }

            dependencies {
                implementation(project(":design_system"))
                implementation(project(":core:common"))

                implementation(libs.findLibrary("androidx-hilt-navigation-compose").get())
                implementation(libs.findLibrary("androidx-navigation-compose").get())
                implementation(libs.findLibrary("androidx-lifecycle-runtime-compose").get())
                implementation(libs.findLibrary("androidx-lifecycle-viewmodel-compose").get())
            }
        }
    }
}