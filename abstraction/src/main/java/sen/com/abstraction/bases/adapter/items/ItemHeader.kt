package sen.com.abstraction.bases.adapter.items

import sen.com.abstraction.bases.adapter.AdapterViewType

/**
 * Created by korneliussendy on 2/14/21,
 * at 1:14 PM.
 * Snap
 */
abstract class ItemHeader : Listable {
    override fun getViewType() = AdapterViewType.TYPE_HEADER
}