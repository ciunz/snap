package sen.com.abstraction.bases.data.model

import sen.com.abstraction.bases.data.Result

/**
 * Created by korneliussendy on 29/09/20,
 * at 19.33.
 * Snap
 */
open class Event<T>(val status: Status, private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun <T> getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content as T
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    enum class Status {
        ROUTE,
        ROUTE_FINISH,
        ROUTE_CLEAR,
        ERROR,
        NORMAL,
        NORMAL_ALERT,
        FATAL_ALERT
    }

    companion object {
        fun normal() = Event(Status.NORMAL, "")
        fun route(route: String) = Event(Status.ROUTE, route)
        fun routeAndFinish(route: String) = Event(Status.ROUTE_FINISH, route)
        fun routeAndClear(route: String) = Event(Status.ROUTE_CLEAR, route)
        fun error(throwable: Throwable) = Event(Status.ERROR, throwable)
        fun error(message: String) = Event(Status.NORMAL_ALERT, message)

        fun <T> cached(data: T): Result<T> {
            return Result.Success(data)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result.Loading()
        }
    }
}