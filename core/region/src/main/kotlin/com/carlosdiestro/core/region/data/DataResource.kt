package com.carlosdiestro.core.region.data

sealed interface DataResource<T> {
    data class Failure<T>(val data: T?, val exception: Throwable) : DataResource<T>
    data class Success<T>(val data: T) : DataResource<T>
}