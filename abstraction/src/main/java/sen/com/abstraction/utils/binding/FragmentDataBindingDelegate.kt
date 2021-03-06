package sen.com.abstraction.utils.binding

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import sen.com.abstraction.bases.ui.BaseActivity
import sen.com.abstraction.bases.ui.BaseFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Created by korneliussendy on 2/12/21,
 * at 11:25 AM.
 * Snap
 */

class FragmentDataBindingDelegate<T : ViewDataBinding>(
    val fragment: BaseFragment
) : ReadOnlyProperty<BaseFragment, T> {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerLiveDataObserver =
                Observer<LifecycleOwner?> {
                    val viewLifecycleOwner = it ?: return@Observer

                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(
                    viewLifecycleOwnerLiveDataObserver
                )
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(
                    viewLifecycleOwnerLiveDataObserver
                )
            }
        })
    }

    override fun getValue(thisRef: BaseFragment, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) return binding

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED))
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")

        return thisRef.dataBind<T>().also { this.binding = it }
    }
}

fun <T : ViewDataBinding> BaseFragment.dataBinding() = FragmentDataBindingDelegate<T>(this)

fun <T : ViewDataBinding> BaseActivity.dataBinding() =
    lazy(LazyThreadSafetyMode.NONE) { this.dataBind<T>() }

fun <T : ViewDataBinding> BaseActivity.dataBinding(type: KClass<T>) =
    lazy<T>(LazyThreadSafetyMode.NONE) { this.dataBind() }