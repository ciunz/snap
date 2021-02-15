package sen.com.abstraction.bases.adapter.viewHolders

import android.view.View
import sen.com.abstraction.bases.adapter.items.Listable

/**
 * Created by korneliussendy on 2/13/21,
 * at 11:12 PM.
 * Snap
 */
class ItemViewHolder<Item : Listable>(view: View) : BaseViewHolder<Item>(view)