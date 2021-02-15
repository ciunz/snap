package sen.com.network

import android.os.Build
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
 */
object OkHttpBuilder {
    private const val REQUEST_TIME_OUT = 60L

    val okHttpClient: OkHttpClient.Builder by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val builder = getOkHttpClientBuilder()
            .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) builder.addInterceptor(httpLoggingInterceptor)
        builder.addNetworkInterceptor { chain ->
            val request = chain.request().newBuilder()
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "Android-${BuildConfig.V_CODE}")
                .addHeader("Accept-Language", "id")//Locale.getDefault().language)
                .addHeader("X-Android-Ver-Name", BuildConfig.V_NAME)
                .addHeader("X-Android-Ver-Code", BuildConfig.V_CODE.toString())
            chain.proceed(request.build())
        }
        builder
    }

    fun getOkHttpClientBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) return builder
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
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}