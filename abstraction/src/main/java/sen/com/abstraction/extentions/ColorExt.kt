package sen.com.abstraction.extentions

import android.content.Context
import android.content.res.ColorStateList
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Created by korneliussendy on 2/9/21,
 * at 3:43 PM.
 * Snap
 */

fun getColorStateList(context: Context, @ColorRes id: Int) =
    ColorStateList.valueOf(ContextCompat.getColor(context, id))
