package sen.com.abstraction.extentions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

/**
 * Created by korneliussendy on 29/09/20,
 * at 16.23.
 * Snap
 */

inline fun <reified T> mutableLiveDataOf(value: T): MutableLiveData<T> =
    MutableLiveData<T>().apply { this.value = value }

fun <T1, T2, R> combineLatest(source1: LiveData<T1>,
                              source2: LiveData<T2>,
                              combiner: Function2<T1, T2, R>): LiveData<R> {
    val mediator = MediatorLiveData<R>()

    val combinerFunction = {
        val source1Value = source1.value
        val source2Value = source2.value
        mediator.value = combiner.apply(source1Value, source2Value)
    }

    mediator.addSource(source1) { combinerFunction() }
    mediator.addSource(source2) { combinerFunction() }
    return mediator
}

interface Function2<T1, T2, R> {
    fun apply(t1: T1?, t2: T2?): R
}