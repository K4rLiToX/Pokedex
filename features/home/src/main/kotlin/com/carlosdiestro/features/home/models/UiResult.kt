package com.carlosdiestro.features.home.models

sealed interface UiResult<out T, out Extra> {
    data object Loading : UiResult<Nothing, Nothing>
    data class Empty<Extra>(val extra: Extra? = null) : UiResult<Nothing, Extra>
    data class Success<T>(val value: T) : UiResult<T, Nothing>
    data class Failure(val exception: Throwable? = null) : UiResult<Nothing, Nothing>
}