package sen.com.abstraction.strategies

import android.widget.ImageView
import androidx.annotation.DrawableRes

/**
 * Created by korneliussendy on 2/7/21,
 * at 2:13 PM.
 * Snap
 */
abstract class ImageLoaderStrategy {

    abstract fun load(
        into: ImageView?,
        imageUri: Any?,
        @DrawableRes placeholderRes: Int? = null,
        @DrawableRes errorRes: Int? = null
    )
}