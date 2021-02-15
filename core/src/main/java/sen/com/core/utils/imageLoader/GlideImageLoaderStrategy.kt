package sen.com.core.utils.imageLoader

import android.widget.ImageView
import sen.com.abstraction.strategies.ImageLoaderStrategy
import sen.com.core.extentions.load

/**
 * Created by korneliussendy on 2/7/21,
 * at 2:17 PM.
 * Snap
 */
object GlideImageLoaderStrategy : ImageLoaderStrategy() {

    override fun load(into: ImageView?, imageUri: Any?, placeholderRes: Int?, errorRes: Int?) {
        into?.load(imageUri, placeholderRes, errorRes)
    }
}