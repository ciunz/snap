package sen.com.network.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import sen.com.network.BuildConfig
import sen.com.network.model.Header
import sen.com.network.model.HttpTimeout
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

/**
 * Created by korneliussendy on 29/06/20,
 * at 23.22.
 * Snap
 */

/**
 * trust all certificate when OS below [Build.VERSION_CODES.M]
 */
@SuppressLint("TrustAllX509TrustManager")
internal fun OkHttpClient.Builder.applySSL() = apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) return@apply
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
        this.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        this.hostnameVerifier { _, _ -> true }
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

/**
 * applying timeout [HttpTimeout] into [OkHttpClient.Builder]
 */
internal fun OkHttpClient.Builder.applyTimeout(httpTimeout: HttpTimeout? = null) = apply {
    httpTimeout?.CONNECT_TIME_OUT?.let { this.connectTimeout(it, TimeUnit.SECONDS) }
    httpTimeout?.READ_TIME_OUT?.let { this.readTimeout(it, TimeUnit.SECONDS) }
    httpTimeout?.WRITE_TIME_OUT?.let { this.writeTimeout(it, TimeUnit.SECONDS) }
}

/**
 * applying listOf [Header] into [OkHttpClient.Builder]
 */
internal fun OkHttpClient.Builder.applyHeader(listHeader: List<Header>? = null) = apply {
    listHeader?.let { headers ->
        this.addNetworkInterceptor { chain ->
            val request = chain.request().newBuilder()
            headers.forEach { header -> request.addHeader(header.key, header.value) }
            chain.proceed(request.build())
        }
    }
}

/**
 * applying [HttpLoggingInterceptor] to log api response.
 * if [BuildConfig.DEBUG] return true, set level to [HttpLoggingInterceptor.Level.BODY]
 * else [HttpLoggingInterceptor.Level.NONE]
 */
internal fun OkHttpClient.Builder.applyLogging() = apply {
    if (BuildConfig.DEBUG) this.addInterceptor(HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    })
}

/**
 * applying [ChuckerInterceptor] only if [BuildConfig.DEBUG] return true
 */
internal fun OkHttpClient.Builder.applyChuck(context: Context?) = apply {
    context?.let { if (BuildConfig.DEBUG) this.addInterceptor(ChuckerInterceptor(context)) }
}