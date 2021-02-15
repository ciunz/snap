package sen.com.abstraction.extentions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by korneliussendy on 25/09/20,
 * at 21.11.
 * Snap
 */

fun AppCompatActivity.toast(any: Any?) {
    Toast.makeText(this, any.toString(), Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.toast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.hideKeyboard() {
    var view = this.currentFocus
    if (view == null) view = View(this)
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}