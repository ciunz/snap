package sen.com.abstraction.bases.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import sen.com.abstraction.R
import sen.com.abstraction.bases.adapter.items.*
import sen.com.abstraction.bases.adapter.viewHolders.*

/**
 * Created by korneliussendy on 2/13/21,
 * at 2:48 PM.
 * Snap
 */

@Suppress("UNCHECKED_CAST")
open class BaseAdapter<Item : Listable> : RecyclerView.Adapter<BaseViewHolder<Item>>() {

    companion object {
        private val defaultLoadingLayout = R.layout.simple_cell_loading
        private val defaultErrorLayout = R.layout.simple_cell_error

        private val defaultViewType = AdapterViewType.values()
        private fun getViewType(code: Int) = defaultViewType.firstOrNull { it.code == code }
    }

    private val items = mutableListOf<Listable>()

    /***********************************
     * BASIC LAYOUT
     ***********************************/

    @LayoutRes
    var mainLayout: Int? = R.layout.simple_cell_empty

    @LayoutRes
    var headerLayout: Int? = null

    @LayoutRes
    var footerLayout: Int? = null

    @LayoutRes
    var loadingLayout: Int? = defaultLoadingLayout

    @LayoutRes
    var errorLayout: Int? = defaultErrorLayout

    var onItemClick: ((View) -> Unit)? = null

    var onBindView: ((View, Item, pos: Int) -> Unit)? = null

    var onBindHeaderView: ((View, Item) -> Unit)? = null

    var onBindFooterView: ((View, Item) -> Unit)? = null

    /***********************************
     * LIST MANIPULATION
     ***********************************/

    fun setList(list: List<Listable>) {
        items.clear()
        items.addAll(list.sortedBy { it.getViewType().code })
        notifyDataSetChanged()
    }

    fun addItem(vararg item: Item) {
        val start = items.indexOfLast { it.getViewType() == AdapterViewType.TYPE_ITEM }
        items.addAll(start, item.asList())
        notifyItemRangeChanged(start, items.lastIndex)
    }

    fun <Header : ItemHeader> addHeader(header: Header) =
        items.add(0, header).also { notifyItemInserted(0) }

    fun clearHeader() = removeType(AdapterViewType.TYPE_HEADER)

    fun <Footer : ItemHeader> addFooter(footer: Footer) =
        items.add(footer).also { notifyItemInserted(items.lastIndex) }

    fun clearFooter() = removeType(AdapterViewType.TYPE_FOOTER)

    private fun removeType(viewType: AdapterViewType) =
        items.removeAll { it.getViewType() == viewType }.also { notifyDataSetChanged() }

    fun clear() = items.clear().also { notifyDataSetChanged() }

    /***********************************
     * LAYOUT AND STUFF
     ***********************************/

    @LayoutRes
    protected open fun getItemLayout(viewType: Int): Int = when (getViewType(viewType)) {
        AdapterViewType.TYPE_ERROR -> errorLayout ?: R.layout.simple_cell_error
        AdapterViewType.TYPE_LOAD -> loadingLayout ?: R.layout.simple_cell_loading
        AdapterViewType.TYPE_HEADER -> headerLayout!!
        AdapterViewType.TYPE_FOOTER -> footerLayout!!
        AdapterViewType.TYPE_ITEM -> mainLayout!!
        else -> R.layout.simple_cell_empty
    }

    override fun getItemViewType(position: Int): Int = items[position].getViewType().code

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Item> {
        val v = LayoutInflater.from(parent.context).inflate(getItemLayout(viewType), parent, false)
        return when (getViewType(viewType)) {
            AdapterViewType.TYPE_ERROR -> ErrorViewHolder(v)
            AdapterViewType.TYPE_LOAD -> LoaderViewHolder(v)
            AdapterViewType.TYPE_HEADER -> HeaderViewHolder(v)
            AdapterViewType.TYPE_FOOTER -> FooterViewHolder(v)
            AdapterViewType.TYPE_ITEM -> BaseViewHolder(v)
            null -> BaseViewHolder(v)
        } as BaseViewHolder<Item>
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<Item>, position: Int) =
        items[position].onBindView(holder.itemView, position)
}