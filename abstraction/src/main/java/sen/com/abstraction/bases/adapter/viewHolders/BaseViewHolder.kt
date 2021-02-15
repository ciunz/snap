package sen.com.abstraction.bases.adapter.viewHolders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import sen.com.abstraction.bases.adapter.items.Listable

/**
 * Created by korneliussendy on 2/13/21,
 * at 2:42 PM.
 * Snap
 */
open class BaseViewHolder<in Item : Listable>(view: View) :
    RecyclerView.ViewHolder(view) {
    open fun bind(
        model: Item,
        onItemClick: ((View) -> Unit)? = null,
        vararg any: Any = emptyArray()
    ) {

    }
}