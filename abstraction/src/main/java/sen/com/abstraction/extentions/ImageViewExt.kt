package sen.com.abstraction.extentions

import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat

/**
 * Created by korneliussendy on 2/9/21,
 * at 3:07 PM.
 * Snap
 */

fun ImageView?.updateTint(@ColorRes id: Int) {
    this?.let { ImageViewCompat.setImageTintList(it, getColorStateList(context, id)) }
}

fun ImageView?.updateSrc(@DrawableRes id: Int) {
    this?.setImageDrawable(ContextCompat.getDrawable(context, id))
}