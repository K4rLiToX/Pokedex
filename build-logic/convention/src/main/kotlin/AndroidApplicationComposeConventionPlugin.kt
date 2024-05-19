import com.android.build.api.dsl.ApplicationExtension
import com.carlosdiestro.pokedex.configureAndroidCompose
import com.carlosdiestro.pokedex.implementation
import com.carlosdiestro.pokedex.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")

            extensions.configure<ApplicationExtension> {
                configureAndroidCompose(this)

                dependencies {
                    implementation(libs.findLibrary("androidx-activity-compose").get())
                }
            }
        }
    }
}