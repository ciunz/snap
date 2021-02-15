package sen.com.abstraction.extentions

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes

/**
 * Created by korneliussendy on 25/09/20,
 * at 15.31.
 * Snap
 */

fun View?.findTextView(@IdRes id: Int) = this?.findViewById<TextView?>(id)
fun View?.findImageView(@IdRes id: Int) = this?.findViewById<ImageView?>(id)
fun View?.findView(@IdRes id: Int) = this?.findViewById<View?>(id)
fun View?.findButton(@IdRes id: Int) = this?.findViewById<Button?>(id)