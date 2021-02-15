package sen.com.abstraction.extentions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by korneliussendy on 26/09/20,
 * at 22.44.
 * Snap
 */
fun EditText.watchText(onTextChanged: (text: String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged(s.toString())
        }
    })
}

fun EditText.watchFocus(onFocusChanged: (focus: Boolean) -> Unit) {
    this.setOnFocusChangeListener { _, b -> onFocusChanged.invoke(b) }
}