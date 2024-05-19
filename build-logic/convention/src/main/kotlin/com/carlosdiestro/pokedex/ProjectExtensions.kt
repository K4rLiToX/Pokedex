package com.carlosdiestro.pokedex

import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun DependencyHandlerScope.implementation(library: Provider<MinimalExternalModuleDependency>) {
    add("implementation", library)
}

internal fun DependencyHandlerScope.ksp(compiler: Provider<MinimalExternalModuleDependency>) {
    add("ksp", compiler)
}

internal fun DependencyHandlerScope.testImplementation(library: Provider<MinimalExternalModuleDependency>) {
    add("testImplementation", library)
}

internal fun DependencyHandlerScope.androidTestImplementation(library: Provider<MinimalExternalModuleDependency>) {
    add("androidTestImplementation", library)
}

internal fun DependencyHandlerScope.debugImplementation(library: Provider<MinimalExternalModuleDependency>) {
    add("debugImplementation", library)
}

internal fun DependencyHandlerScope.implementation(project: Project) {
    add("implementation", project)
}

