package sen.com.abstraction.strategies

import sen.com.abstraction.bases.data.Result

/**
 * Created by korneliussendy on 28/09/20,
 * at 15.04.
 * Snap
 */
interface BaseAPIParserStrategy {
    suspend fun <U, T> parse(response: U): Result<T>
}