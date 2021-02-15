package sen.com.abstraction.bases.adapter.items

import android.view.View
import sen.com.abstraction.bases.adapter.AdapterViewType

/**
 * Created by korneliussendy on 2/14/21,
 * at 12:58 PM.
 * Snap
 */
interface Listable {
    fun getViewType(): AdapterViewType = AdapterViewType.TYPE_ITEM
    fun onBindView(view: View, pos: Int, onItemClick: ((View) -> Unit)? = null) {}
}