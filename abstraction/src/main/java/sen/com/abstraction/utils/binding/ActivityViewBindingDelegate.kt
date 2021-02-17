package sen.com.abstraction.utils.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import sen.com.abstraction.bases.ui.BaseActivity
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Created by korneliussendy on 2/12/21,
 * at 11:25 AM.
 * Snap
 */

class ActivityViewBindingDelegate<T : ViewBinding>(
    private val activity: BaseActivity,
    private val clazz: KClass<T>
) : ReadOnlyProperty<BaseActivity, T> {
    private var binding: T? = null

    init {
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
            }

            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                // invoking binding if not initialized
                if (binding == null) {
                    binding = clazz.java.getBinding(
                        activity.layoutInflater,
                        activity.viewContent as ViewGroup,
                        true
                    )
                }
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

        return clazz.java.getBinding<T>(
            thisRef.layoutInflater,
            thisRef.viewContent as ViewGroup,
            true
        ).also { this.binding = it }
    }
}

inline fun <reified T : ViewBinding> BaseActivity.viewBinding() =
    ActivityViewBindingDelegate(this, T::class)

fun <T : ViewBinding> BaseActivity.viewBinding(clazz: KClass<T>) =
    ActivityViewBindingDelegate(this, clazz)

private fun <T : ViewBinding> Class<*>.getBinding(
    layoutInflater: LayoutInflater,
    container: ViewGroup?,
    attachToRoot: Boolean
): T {
    return try {
        @Suppress("UNCHECKED_CAST")
        getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, layoutInflater, container, attachToRoot) as T
    } catch (ex: Exception) {
        throw RuntimeException("The ViewBinding inflate function has been changed.", ex)
    }
}