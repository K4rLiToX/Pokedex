package com.carlosdiestro.pokemon.domain

sealed interface Event<out T> {

    data class Success<T>(val data: T) : Event<T>
    data object DataNotAvailable : Event<Nothing>
    data object DataNotModified : Event<Nothing>
}