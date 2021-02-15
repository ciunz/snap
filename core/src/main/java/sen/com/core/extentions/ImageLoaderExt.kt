package sen.com.core.extentions

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestOptions

/**
 * Created by korneliussendy on 2/7/21,
 * at 1:50 AM.
 * Snap
 */

fun ImageView.load(
    imageUri: Any?,
    @DrawableRes placeholderRes: Int? = null,
    @DrawableRes errorRes: Int? = null
) {
    val builder = generateBuilder(this, imageUri)

    placeholderRes?.let { builder.placeholder(it) }
    errorRes?.let { builder.error(errorRes) }
    builder
        .apply(RequestOptions())
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .into(this)
}

private fun generateBuilder(view: View, imageUri: Any?) =
    if (imageUri is String && imageUri.isNotEmpty()) Glide.with(view)
        .load(GlideUrlCustomCacheKey(imageUri))
    else Glide.with(view).load(imageUri)


class GlideUrlCustomCacheKey(url: String) : GlideUrl(url) {
    override fun getCacheKey(): String {
        val url = toStringUrl()
        return if (url.contains("?")) url.substring(0, url.lastIndexOf("?"))
        else url
    }
}