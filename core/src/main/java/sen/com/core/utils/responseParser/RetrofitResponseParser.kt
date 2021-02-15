package sen.com.core.utils.responseParser

import retrofit2.Response
import sen.com.abstraction.strategies.BaseAPIParserStrategy
import sen.com.abstraction.bases.data.Result

/**
 * Created by korneliussendy on 2/4/21,
 * at 1:54 PM.
 * Snap
 */
object RetrofitResponseParser : BaseAPIParserStrategy {

    override suspend fun <U, T> parse(response: U): Result<T> {
        if (response !is Response<*>) return Result.Error("Invalid API Object")
        try {
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body as T)
            }
            return Result.Error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return Result.Error(e.message ?: e.toString())
        }
    }
}