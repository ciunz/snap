package sen.com.abstraction.bases.data.dataSource

import sen.com.abstraction.strategies.BaseAPIParserStrategy
import sen.com.abstraction.bases.data.Result

/**
 * Created by korneliussendy on 25/09/20,
 * at 23.12.
 * Snap
 */

abstract class BaseRemoteDataSource {

    protected open suspend fun <T> getResult(
        parser: BaseAPIParserStrategy,
        call: suspend () -> Any
    ): Result<T> {
        return try {
            parser.parse(call())
        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }

    protected fun <T> error(message: String): Result<T> {
        return Result.Error("Network call has failed for a following reason: $message")
    }

}