package sen.com.abstraction.bases.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

/**
 * Created by korneliussendy on 26/09/20,
 * at 12.51.
 * Snap
 */
fun <T> fetch(
    networkCall: (suspend () -> Result<T>)? = null,
    databaseQuery: (suspend () -> Result<T>)? = null,
    onComplete: (suspend (T) -> Unit)? = null,
    localFirst: Boolean = false
): LiveData<Result<T>> = liveData(Dispatchers.IO) {
    //first thing first emit loading
    emit(Result.Loading<T>())
    //get data form local storage / database
    val local = databaseQuery?.invoke()
    //flag if local data existed
    val hasLocalData = local != null && local !is Result.Error && local.data != null

    // emit cache if not error and localFirst flagged on or network call is null
    local?.let { if (local !is Result.Error && (localFirst || networkCall == null)) emit(local) }

    //get data form api network
    val remote = networkCall?.invoke()

    remote?.let {
        if (it is Result.Error) {
            // when remote error try to salvage with local data
            if (hasLocalData) emit(local!!)
            // show error only if no local was emitted
            else if (!localFirst) emit(it)
        } else {
            // when remote data retrieved, best to save to loca
            it.data?.let { data -> onComplete?.invoke(data) }
            // sending success response
            emit(it)
        }
    }
}

/**
 *  to get data from and only from network call
 */
fun <T> fresh(
    networkCall: suspend () -> Result<T>,
    onComplete: (suspend (T) -> Unit)? = null
) = fetch(networkCall = networkCall, onComplete = onComplete)

fun <T> fresh(networkCall: suspend () -> Result<T>) = fetch(networkCall = networkCall)

/**
 *  to get data from and only from database
 */
fun <T> cached(databaseQuery: suspend () -> Result<T>) = fetch(databaseQuery = databaseQuery)

/**
 *  to get data first from local and then update it with network
 */
fun <T> cachedFirst(
    networkCall: (suspend () -> Result<T>),
    databaseQuery: (suspend () -> Result<T>),
    saveCallResult: (suspend (T) -> Unit)? = null
) = fetch(networkCall, databaseQuery, saveCallResult, true)