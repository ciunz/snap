package sen.com.abstraction.extentions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import sen.com.abstraction.R

/**
 * Created by korneliussendy on 25/09/20,
 * at 16.04.
 * Snap
 */

fun AppCompatActivity.startActivity(
    direction: String,
    finish: Boolean = false,
    requestCode: Int? = null,
    extra: Bundle? = null,
    flag: Int? = null
) {
    try {
        val uri = getString(R.string.base_scheme) + "://" + direction
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        extra?.let { intent.putExtras(it) }
        flag?.let { intent.flags = it }
        if (requestCode == null || requestCode == 0)
            startActivity(intent)
        else
            startActivityForResult(intent, requestCode)
        if (finish) finish()
    } catch (e: Exception) {
        Log.e("startActivity", e.message, e.cause)
    }
}

fun Fragment.startActivity(
    direction: String,
    finish: Boolean = false,
    requestCode: Int? = null,
    extra: Bundle? = null,
    flag: Int? = null
) {
    try {
        val uri = getString(R.string.base_scheme) + "://" + direction
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        extra?.let { intent.putExtras(it) }
        flag?.let { intent.flags = it }
        if (requestCode == null || requestCode == 0)
            startActivity(intent)
        else
            startActivityForResult(intent, requestCode)
        if (finish) activity?.finish()
    } catch (e: Exception) {
        Log.e("startActivity", e.message, e.cause)
    }
}