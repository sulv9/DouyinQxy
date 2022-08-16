package com.qxy.lib.base.base.network

data class Results<out T>(
    val state: State,
    val data: T?,
) {
    sealed class State {
        object LOADING : State()
        object SUCCESS : State()
        object NONE: State()
        data class ERROR(val errors: Errors) : State()
    }

    companion object {
        fun loading() = Results(State.LOADING, null)

        fun <T> success(data: T) = Results(State.SUCCESS, data)

        fun error(errors: Errors) = Results(State.ERROR(errors), null)

        fun none() = Results(State.NONE, null)
    }
}

inline fun <T> processResults(block: () -> T): Results<T> {
    return try {
        Results.success(block.invoke())
    } catch (e: Exception) {
        Results.error(
            when(e) {
                is Errors.ApiError -> e
                is Errors.NetworkError -> e
                is Errors.EmptyResultError -> e
                else -> Errors.UnknownError(e)
            }
        )
    }
}

inline fun <T> Results<T>.ifSuccess(block: Results<T>.() -> Results<T>) =
    if (this.state is Results.State.SUCCESS) block(this) else this

inline fun <T> Results<T>.ifEmpty(block: () -> Results<T>): Results<T> where T : Collection<*> =
    if (data != null && data.isEmpty()) block() else this