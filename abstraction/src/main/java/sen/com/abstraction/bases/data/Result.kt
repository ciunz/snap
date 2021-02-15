package sen.com.abstraction.bases.data

import java.lang.Exception

/**
 * Created by korneliussendy on 25/09/20,
 * at 23.07.
 * Snap
 */

sealed class Result<out T>(
    val data: T?,
    val message: String?,
    val exception: Exception? = null
) {

    class Loading<T> : Result<T>(null, null)
    class Cached<T>(data: T?) : Result<T>(data, null)
    class Success<T>(data: T?) : Result<T>(data, null)
    class Error<T>(message: String?, exception: Exception?) : Result<T>(null, message, exception) {
        constructor(e: Exception) : this(e.localizedMessage ?: e.message, e)
        constructor(message: String) : this(message, null)
    }
}