package sen.com.abstraction.strategies

/**
 * Created by korneliussendy on 2/2/21,
 * at 2:16 PM.
 * Snap
 */
interface JSONParserStrategy {
    suspend fun <T> parse(json: String?): T?
    suspend fun <T> convert(obj: T?): String?
}