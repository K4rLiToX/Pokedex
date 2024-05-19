import com.carlosdiestro.pokedex.implementation
import com.carlosdiestro.pokedex.ksp
import com.carlosdiestro.pokedex.libs
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.google.devtools.ksp")

            extensions.configure<KspExtension> {
                arg("room.schemaLocation", "$projectDir/schemas")
            }

            dependencies {
                implementation(libs.findLibrary("androidx-room-runtime").get())
                implementation(libs.findLibrary("androidx-room-ktx").get())
                ksp(libs.findLibrary("androidx-room-compiler").get())
            }
        }
    }
}