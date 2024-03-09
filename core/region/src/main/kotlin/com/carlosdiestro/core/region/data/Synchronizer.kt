package com.carlosdiestro.core.region.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <ResultType, RequestType> synchronizer(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> Result<RequestType>,
    crossinline cache: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val localData = query().first()

    val flow = if (shouldFetch(localData)) {
        fetch().fold(
            onSuccess = { remoteRegions ->
                cache(remoteRegions)
                query().map { DataResource.Success(it) }
            },
            onFailure = { e ->
                query().map { DataResource.Failure(it, e) }
            }
        )
    } else {
        query().map { DataResource.Success(it) }
    }
    emitAll(flow)
}

inline fun <ResultType, RequestType, MappedType> mapSynchronizer(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> Result<RequestType>,
    crossinline cache: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline mapper: (ResultType?) -> MappedType
) = flow {
    val localData = query().first()

    val flow = if (shouldFetch(localData)) {
        fetch().fold(
            onSuccess = { remoteRegions ->
                cache(remoteRegions)
                query().map {
                    DataResource.Success(mapper(it))
                }
            },
            onFailure = { e ->
                query().map { DataResource.Failure(mapper(it), e) }
            }
        )
    } else {
        query().map { DataResource.Success(mapper(it)) }
    }
    emitAll(flow)
}