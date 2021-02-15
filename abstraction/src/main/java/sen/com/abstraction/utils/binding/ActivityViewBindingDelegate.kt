package sen.com.abstraction.utils.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import sen.com.abstraction.bases.ui.BaseActivity
import java.util.zip.Inflater
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by korneliussendy on 2/12/21,
 * at 11:25 AM.
 * Snap
 */

class ActivityViewBindingDelegate<T : ViewBinding>(
    private val activity: BaseActivity,
    private val factory: (LayoutInflater, ViewGroup?, Boolean) -> T
) : ReadOnlyProperty<BaseActivity, T> {
    private var binding: T? = null

    init {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
            }

            override fun onDestroy(owner: LifecycleOwner) {
                binding = null
                activity.lifecycle.removeObserver(this)
            }
        })
    }

    override fun getValue(thisRef: BaseActivity, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        val lifecycle = activity.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
        }

        return factory.invoke(thisRef.layoutInflater, thisRef.viewContent as ViewGroup, true)
            .also { this.binding = it }
    }
}

fun <T : ViewBinding> BaseActivity.viewBinding(factory: (LayoutInflater, ViewGroup?, Boolean) -> T) =
    ActivityViewBindingDelegate(this, factory)