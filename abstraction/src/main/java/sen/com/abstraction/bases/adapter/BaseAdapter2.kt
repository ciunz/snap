package sen.com.abstraction.bases.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import sen.com.abstraction.R
import sen.com.abstraction.extentions.findButton
import sen.com.abstraction.extentions.findTextView
import sen.com.abstraction.utils.ViewUtils.visibleOrGone
import sen.com.abstraction.utils.onClickIf

/**
 * Created by korneliussendy on 2/6/21,
 * at 1:34 PM.
 * Snap
 */
abstract class BaseAdapter2<I>(private var items: ArrayList<I?> = ArrayList()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val defaultLoadingLayout = R.layout.simple_cell_loading
    private val defaultErrorLayout = R.layout.simple_cell_error

    /***********************************
     * BASIC LAYOUT
     ***********************************/

    @LayoutRes
    protected abstract fun mainLayout(): Int?

    @LayoutRes
    protected open fun loadingLayout(): Int? = defaultLoadingLayout

    @LayoutRes
    protected open fun errorLayout(): Int? = defaultErrorLayout

    /***********************************
     * ITEM MANIPULATION
     ***********************************/

    fun getItems(): ArrayList<I?> = items
    fun isEmpty() = itemCount == 0
    fun getItem(pos: Int): I? = items.getOrNull(pos)

    open fun addItem(item: I?) {
        initHeader()
        items.add(item)
        notifyItemInserted(itemCount - 1)
    }

    open fun addItem(item: I?, pos: Int) {
        if (pos >= items.size) return
        items.add(pos, item)
        notifyItemInserted(pos)
    }

    open fun addItems(items: List<I>) {
        if (items.isNotEmpty()) initHeader()
        val count = itemCount
        this.items.addAll(items)
        notifyItemRangeInserted(count, items.size)
    }

    open fun updateItem(item: I?, position: Int) {
        if (position > items.size - 1) return
        items[position] = item
        notifyItemChanged(position)
    }


    protected fun removeLast() {
        var firstIndex = 0
        if (withHeader() && headerInitialized) firstIndex = 1
        for (i in 1..(loadingItemCount()))
            if (itemCount > firstIndex) {
                items.removeAt(itemCount - 1)
                notifyItemRemoved(itemCount)
            }
    }

    open fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
    fun notifyConfigChanged() = notifyItemRangeChanged(0, items.size)

    /***********************************
     * LOADER
     ***********************************/

    private var loadingMessage: String? = null
    open fun loadingItemCount(): Int = 1

    fun showLoader(message: String = "Memuat") {
        hideError()
        loadingMessage = message
        for (i in 1..(loadingItemCount())) addItem(null)
    }

    fun hideLoader() {
        removeLast()
        loadingMessage = ""
    }

    /***********************************
     * HEADER
     ***********************************/

    @LayoutRes
    protected open var headerLayout: Int? = null
    private fun withHeader() = headerLayout != null
    protected open val onBindHeader: ((View) -> Unit)? = null
    protected open var setupHeader: ((v: View) -> Unit)? = null
    private var headerInitialized = false

    private fun initHeader() {
        if (withHeader() && !headerInitialized) {
            this.items.add(0, null)
            headerInitialized = true
            notifyItemInserted(0)
        }
    }

    fun showHeader(@LayoutRes headerRes: Int, setupHeader: ((v: View) -> Unit)? = null) {
        this.headerLayout = headerRes
        this.setupHeader = setupHeader
    }

    /***********************************
     * ERROR
     ***********************************/

    private var errorMessage: String? = null
    private var errorButtonText: String? = null
    protected var error = false
    private var onErrorClick: (() -> Unit)? = null

    fun showError(
        errorMessage: String? = null,
        btnText: String? = null,
        onErrorClick: (() -> Unit)? = null
    ) {
        hideError()
        this.errorMessage = errorMessage
        this.onErrorClick = onErrorClick
        this.errorButtonText = btnText
        error = true
        addItem(null)
    }

    fun hideError() {
        if (!error) return
        this.errorMessage = ""
        error = false
        removeLast()
    }

    /***********************************
     * ADAPTER FUNCTIONS
     ***********************************/

    override fun getItemViewType(position: Int): Int {
        val hasItem = items[position] != null
        val isHeader = withHeader() && position == 0
        return when {
            !hasItem && error -> TYPE_ERROR
            !hasItem && isHeader -> TYPE_HEADER
            !hasItem -> TYPE_LOAD
            else -> TYPE_ITEM
        }
    }

    @LayoutRes
    protected open fun getItemLayout(viewType: Int): Int = when (viewType) {
        TYPE_ERROR -> errorLayout() ?: R.layout.simple_cell_error
        TYPE_LOAD -> loadingLayout() ?: R.layout.simple_cell_loading
        TYPE_HEADER -> headerLayout ?: mainLayout()!!
        else -> mainLayout()!!
    }

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val resLayout = getItemLayout(viewType)
        val v = inflater.inflate(resLayout, parent, false)
        return when (viewType) {
            TYPE_ERROR -> ErrorViewHolder(v, errorLayout() != defaultErrorLayout)
            TYPE_LOAD -> LoaderViewHolder(v, loadingLayout() != defaultLoadingLayout)
            TYPE_HEADER -> HeaderViewHolder(v, setupHeader)
            else -> BaseViewHolder2(v)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when {
            holder is LoaderViewHolder -> holder.bind(loadingMessage)
            holder is ErrorViewHolder -> holder.bind(errorMessage, errorButtonText, onErrorClick)
            holder is HeaderViewHolder && withHeader() -> holder.bind()
            else -> items[position]?.let { onBindView(holder.itemView, it, position) }
        }
    }

    abstract fun onBindView(v: View, item: I, pos: Int)

    companion object {
        private const val TYPE_ERROR = -1
        private const val TYPE_LOAD = 0
        private const val TYPE_HEADER = 2
        protected const val TYPE_ITEM = 1

        class BaseViewHolder2(v: View) : RecyclerView.ViewHolder(v)

        internal class LoaderViewHolder(private var v: View, private var custom: Boolean) :
            RecyclerView.ViewHolder(v) {
            fun bind(msg: String?) {
                if (!custom) v.findTextView(R.id.tvInfo)?.let {
                    it.visibility = if (msg.isNullOrEmpty()) View.GONE else View.VISIBLE
                    it.text = msg
                }
            }
        }

        internal class ErrorViewHolder(var v: View, private var custom: Boolean) :
            RecyclerView.ViewHolder(v) {
            fun bind(msg: String?, btnText: String?, onErrorClick: (() -> Unit)?) {
                if (!custom) v.findTextView(R.id.tvInfoError)?.let {
                    it.visibility = if (msg.isNullOrEmpty()) View.GONE else View.VISIBLE
                    it.text = msg
                }
                v.findButton(R.id.btnAction)?.let {
                    it.text = btnText
                    visibleOrGone(onErrorClick != null, it)
                    it.onClickIf(onErrorClick != null) { onErrorClick?.invoke() }
                }
            }
        }

        internal class HeaderViewHolder(
            private var v: View, private var callback: ((View) -> Unit)?
        ) : RecyclerView.ViewHolder(v) {
            fun bind() = callback?.invoke(v)
        }
    }
}