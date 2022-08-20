package com.qxy.lib.base.base.network

sealed class Results<out T> {

    data class Success<out T>(val value: T) : Results<T>()

    data class Failure(val errors: Errors) : Results<Nothing>()

    object Loading : Results<Nothing>()

    object None : Results<Nothing>()

    data class LastResult<out T>(val value: Results<T>): Results<T>()

}

inline fun <T> processResults(block: () -> T): Results<T> {
    return try {
        Results.Success(block.invoke())
    } catch (e: Errors) {
        Results.Failure(e)
    }
}

inline fun <T> Results<T>.whenSuccess(success: (T) -> Unit) {
    if (this is Results.Success) {
        success(value)
    }
}

inline fun <T> Results<T>.whenFailure(failure: (Errors) -> Unit) {
    if (this is Results.Failure) {
        failure(errors)
    }
}

inline fun <T> Results<T>.whenLoading(loading: () -> Unit) {
    if (this is Results.Loading) {
        loading()
    }
}

inline fun <T> Results<T>.whenNone(none: () -> Unit) {
    if (this is Results.None) {
        none()
    }
}

inline fun <T> Results<T>.ifEmpty(block: () -> Results<T>): Results<T> where T : Collection<*> =
    if (this is Results.Success && this.value.isEmpty()) block() else this