package sen.com.abstraction.bases.adapter.items

import android.view.View
import sen.com.abstraction.R
import sen.com.abstraction.bases.adapter.AdapterViewType
import sen.com.abstraction.extentions.findButton
import sen.com.abstraction.extentions.findTextView
import sen.com.abstraction.utils.ViewUtils
import sen.com.abstraction.utils.onClickIf

/**
 * Created by korneliussendy on 2/14/21,
 * at 1:14 PM.
 * Snap
 */
data class ItemError(val message: String, val action: String, val onAction: () -> Unit) : Listable {
    override fun getViewType() = AdapterViewType.TYPE_ERROR
    override fun onBindView(view: View, pos: Int, onItemClick: ((View) -> Unit)?) {
        view.findTextView(R.id.tvInfoError)?.text = message
        view.findButton(R.id.btnAction)?.let {
            ViewUtils.visibleOrGone(action != null, it)
            it.text = action
            it.onClickIf(action != null) { onAction?.invoke() }
        }
    }
}