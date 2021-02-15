package sen.com.abstraction.bases.adapter.items

import android.view.View
import sen.com.abstraction.R
import sen.com.abstraction.bases.adapter.AdapterViewType
import sen.com.abstraction.extentions.findTextView

/**
 * Created by korneliussendy on 2/14/21,
 * at 1:14 PM.
 * Snap
 */
class ItemLoader(val message: String? = "Loading") : Listable {
    override fun getViewType() = AdapterViewType.TYPE_LOAD
    override fun onBindView(view: View, pos: Int, onItemClick: ((View) -> Unit)?) {
        view.findTextView(R.id.tvInfo)?.text = message
    }
}