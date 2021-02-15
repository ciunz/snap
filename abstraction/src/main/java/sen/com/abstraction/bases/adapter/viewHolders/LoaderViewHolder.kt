package sen.com.abstraction.bases.adapter.viewHolders

import android.view.View
import sen.com.abstraction.R
import sen.com.abstraction.bases.adapter.items.ItemLoader
import sen.com.abstraction.extentions.findTextView
import sen.com.abstraction.utils.ViewUtils.visibleOrGone

/**
 * Created by korneliussendy on 2/13/21,
 * at 11:12 PM.
 * Snap
 */
class LoaderViewHolder(val view: View) : BaseViewHolder<ItemLoader>(view) {
    override fun bind(model: ItemLoader, onItemClick: ((View) -> Unit)?, vararg any: Any) {
        view.findTextView(R.id.tvInfo)?.let {
            visibleOrGone(!model.message.isNullOrEmpty(), it)
            it.text = model.message
        }
    }
}