package sen.com.core.utils.jsonParser

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sen.com.abstraction.BuildConfig
import sen.com.abstraction.strategies.JSONParserStrategy

/**
 * Created by korneliussendy on 2/2/21,
 * at 3:43 PM.
 * Snap
 */
object GSONParserStrategy : JSONParserStrategy {
    private val gson = Gson()
    override suspend fun <T> parse(json: String?): T? = withContext(Dispatchers.IO) {
        try {
            gson.fromJson<T>(json, object : TypeToken<T>() {}.type)
        } catch (e: Exception) {
            if (BuildConfig.DEBUG) Log.e("GSON", e.message, e)
            null
        }
    }

    override suspend fun <T> convert(obj: T?): String? = withContext(Dispatchers.IO) {
        obj?.let { gson.toJson(obj) }
    }
}