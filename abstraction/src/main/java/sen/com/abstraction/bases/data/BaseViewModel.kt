package sen.com.abstraction.bases.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sen.com.abstraction.bases.data.model.Event

/**
 * Created by korneliussendy on 26/09/20,
 * at 12.38.
 * Snap
 */
abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData(false)
    var loading: LiveData<Boolean> = _loading
    fun showLoading() = run { _loading.value = true }
    fun hideLoading() = run { _loading.value = false }
    fun updateLoading(value: Boolean) = run { _loading.value = value }

    private val _event = MutableLiveData<Event<*>>()
    val event: LiveData<Event<*>> = _event
    fun dispatchEvent(event: Event<*>) {
        _event.value = event
    }

    abstract fun onReady()
}