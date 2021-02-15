package sen.com.abstraction.bases.adapter

import android.view.View
import androidx.annotation.LayoutRes
import sen.com.abstraction.bases.adapter.items.*

/**
 * Created by korneliussendy on 2/15/21,
 * at 10:56 AM.
 * Snap
 */
sealed class ViewType<Item : Listable>(
    val code: Int,
    val name: String,
    @LayoutRes val resId: Int,
    val onBind: (View, Item) -> Unit
)

open class TypeHeader(@LayoutRes resId: Int, onBind: (View, ItemHeader) -> Unit) :
    ViewType<ItemHeader>(0, "HEADER", resId, onBind)

open class TypeItem<Item : Listable>(@LayoutRes resId: Int, onBind: (View, Item) -> Unit) :
    ViewType<Item>(1, "ITEM", resId, onBind)

open class TypeLoader(@LayoutRes resId: Int, onBind: (View, ItemLoader) -> Unit) :
    ViewType<ItemLoader>(2, "LOADER", resId, onBind)

open class TypeError(@LayoutRes resId: Int, onBind: (View, ItemError) -> Unit) :
    ViewType<ItemError>(3, "ERROR", resId, onBind)

open class TypeFooter(@LayoutRes resId: Int, onBind: (View, ItemFooter) -> Unit) :
    ViewType<ItemFooter>(99, "FOOTER", resId, onBind)

enum class AdapterViewType(val code: Int) {
    TYPE_HEADER(0),
    TYPE_ITEM(1),
    TYPE_LOAD(2),
    TYPE_ERROR(3),
    TYPE_FOOTER(99),
}