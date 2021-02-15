package sen.com.abstraction.extentions

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt

/**
 * Created by korneliussendy on 25/09/20,
 * at 15.31.
 * Snap
 */

fun View?.findTextView(@IdRes id: Int) = this?.findViewById<TextView?>(id)
fun View?.findImageView(@IdRes id: Int) = this?.findViewById<ImageView?>(id)
fun View?.findView(@IdRes id: Int) = this?.findViewById<View?>(id)
fun View?.findButton(@IdRes id: Int) = this?.findViewById<Button?>(id)
fun View?.updateBackgroundColor(color: String) = this?.setBackgroundColor(color.toColorInt())
fun View?.updateBackgroundColor(@ColorRes colorRes: Int) =
    this?.setBackgroundColor(ContextCompat.getColor(context, colorRes))