package sen.com.network.model

/**
 * Created by korneliussendy on 2/15/21,
 * at 1:38 PM.
 * Ajaib-MVVM
 */
data class HttpTimeout(
    val READ_TIME_OUT: Long? = 30L,
    val CONNECT_TIME_OUT: Long? = 10L,
    val WRITE_TIME_OUT: Long? = 10L
)
