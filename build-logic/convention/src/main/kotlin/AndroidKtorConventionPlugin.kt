import com.carlosdiestro.pokedex.implementation
import com.carlosdiestro.pokedex.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidKtorConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(libs.findLibrary("ktor-client-core").get())
                implementation(libs.findLibrary("ktor-client-android").get())
                implementation(libs.findLibrary("ktor-client-content-negotiation").get())
                implementation(libs.findLibrary("ktor-serialization-kotlinx-json").get())
            }
        }
    }
}