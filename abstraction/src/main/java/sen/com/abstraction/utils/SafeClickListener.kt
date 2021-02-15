package sen.com.abstraction.utils

import android.os.SystemClock
import android.view.View

/**
 * Created by korneliussendy on 2019-07-04,
 * at 20:23.
 * Snap
 */
class SafeClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        onClick(v, SystemClock.elapsedRealtime())
    }

    fun onClick(v: View, elapseTime: Long) {
        if (elapseTime - lastTimeClicked >= defaultInterval) {
            lastTimeClicked = elapseTime
            onSafeCLick(v)
        }
    }
}

fun View.onClick(onSafeClick: (View) -> Unit): SafeClickListener {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
    return safeClickListener
}

fun View.onClick(interval: Int, onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener(interval) {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun View.onClickIf(enable: Boolean, onSafeClick: (View) -> Unit) {
    if (!enable) return removeOnClick()
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun View.removeOnClick() = setOnClickListener(null)

fun onClick(vararg views: View?, onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    views.forEach { view ->
        view?.setOnClickListener(safeClickListener)
    }
}