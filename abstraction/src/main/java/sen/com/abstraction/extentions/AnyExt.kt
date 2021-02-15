package sen.com.abstraction.extentions

import android.util.Log
import sen.com.abstraction.BuildConfig

/**
 * Created by korneliussendy on 27/09/20,
 * at 16.10.
 * Snap
 */

fun Any?.log(any: Any?) {
    Log.d(BuildConfig.V_NAME, any.toString())
}