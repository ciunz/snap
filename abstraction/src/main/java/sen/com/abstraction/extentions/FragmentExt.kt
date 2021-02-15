package sen.com.abstraction.extentions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * Created by korneliussendy on 2/15/21,
 * at 12:53 PM.
 * Snap
 */

fun Fragment.toast(any: Any?) {
    Toast.makeText(requireContext(), any.toString(), Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(@StringRes resId: Int) {
    Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show()
}