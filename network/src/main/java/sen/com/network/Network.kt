package sen.com.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sen.com.network.model.ApiURL
import sen.com.network.model.Header
import sen.com.network.model.HttpTimeout
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

/**
 * Created by korneliussendy on 25/09/20,
 * at 19.57.
 * Snap
 */

fun httpClient(
    context: WeakReference<Context>?,
    httpTimeout: HttpTimeout? = null,
    listHeader: List<Header>? = null
): OkHttpClient {

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level =
        if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE

    val builder = OkHttpBuilder.getOkHttpClientBuilder()
    httpTimeout?.CONNECT_TIME_OUT?.let { builder.connectTimeout(it, TimeUnit.SECONDS) }
    httpTimeout?.READ_TIME_OUT?.let { builder.readTimeout(it, TimeUnit.SECONDS) }
    httpTimeout?.WRITE_TIME_OUT?.let { builder.writeTimeout(it, TimeUnit.SECONDS) }

    builder.addInterceptor(httpLoggingInterceptor)
    if (BuildConfig.DEBUG && context?.get() != null) {
        builder.addInterceptor(ChuckerInterceptor(context.get()!!))
    }
    listHeader?.let { headers ->
        builder.addNetworkInterceptor { chain ->
            val request = chain.request().newBuilder()
            headers.forEach { header -> request.addHeader(header.key, header.value) }
            chain.proceed(request.build())
        }
    }
    return builder.build()
}

inline fun <reified T> services(
    context: Context? = null,
    apiURL: ApiURL,
    httpTimeout: HttpTimeout? = null,
    listHeader: List<Header>? = null
): T {
    val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setLenient()
        .create()

    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(apiURL.url)
        .addConverterFactory(GsonConverterFactory.create(gson))
    retrofitBuilder.client(
        if (context != null && BuildConfig.DEBUG) httpClient(
            WeakReference(context),
            httpTimeout,
            listHeader
        )
        else OkHttpBuilder.okHttpClient.build()

    )
    val retrofit = retrofitBuilder.build()
    return retrofit.create(T::class.java)
}
