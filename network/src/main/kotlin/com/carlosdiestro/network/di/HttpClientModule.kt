package com.carlosdiestro.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

private const val TimeOut = 10_000

@Module
@InstallIn(SingletonComponent::class)
internal object HttpClientModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient =
        HttpClient(Android) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    prettyPrint = true
                })
            }
            install(HttpCache)
            engine {
                socketTimeout = TimeOut
                connectTimeout = TimeOut
            }
        }
}