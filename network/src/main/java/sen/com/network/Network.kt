package sen.com.network

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sen.com.network.model.ApiURL
import sen.com.network.model.Header
import sen.com.network.model.HttpTimeout
import sen.com.network.utils.*
import java.lang.ref.WeakReference

/**
 * Created by korneliussendy on 25/09/20,
 * at 19.57.
 * Snap
 */

fun httpClient(
    context: WeakReference<Context>?,
    httpTimeout: HttpTimeout? = null,
    listHeader: List<Header>? = null
): OkHttpClient = OkHttpClient.Builder().apply {
    applySSL()
    applyTimeout(httpTimeout)
    applyLogging()
    applyHeader(listHeader)
    context?.get()?.let { this.applyChuck(it) }
}.build()


inline fun <reified T> services(
    context: Context? = null,
    apiURL: ApiURL,
    httpTimeout: HttpTimeout? = null,
    listHeader: List<Header>? = null
): T {
    val converterFactory = GsonConverterFactory.create(
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setLenient()
            .create()
    )

    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(apiURL.url)
        .addConverterFactory(converterFactory)

    retrofitBuilder.client(
        httpClient(
            if (context != null && BuildConfig.DEBUG) WeakReference(context) else null,
            httpTimeout,
            listHeader
        )
    )
    val retrofit = retrofitBuilder.build()
    return retrofit.create(T::class.java)
}
