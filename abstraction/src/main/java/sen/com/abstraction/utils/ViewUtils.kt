package sen.com.abstraction.utils

import android.view.View

/**
 * Created by korneliussendy on 25/09/20,
 * at 13.21.
 * Snap
 */
object ViewUtils {
    fun visible(vararg views: View?) = views.forEach { it?.visibility = View.VISIBLE }
    fun gone(vararg views: View?) = views.forEach { it?.visibility = View.GONE }
    fun invisible(vararg views: View?) = views.forEach { it?.visibility = View.INVISIBLE }
    fun disable(vararg views: View?) = views.forEach { it?.isEnabled = false }
    fun enable(vararg views: View?) = views.forEach { it?.isEnabled = true }
    fun enableIf(conditionForEnable: Boolean, vararg views: View?) =
        views.forEach { it?.isEnabled = conditionForEnable }

    fun disableIf(conditionForDisable: Boolean, vararg views: View?) =
        views.forEach { it?.isEnabled = !conditionForDisable }

    fun visibleOrGone(conditionForVisible: Boolean, vararg views: View?) =
        if (conditionForVisible) visible(*views) else gone(*views)

    fun visibleOrInvisible(conditionForVisible: Boolean, vararg views: View?) =
        if (conditionForVisible) visible(*views) else invisible(*views)
}