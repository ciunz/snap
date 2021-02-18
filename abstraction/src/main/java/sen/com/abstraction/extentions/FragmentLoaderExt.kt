package sen.com.abstraction.extentions

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

/**
 * Created by korneliussendy on 2/18/21,
 * at 10:35 PM.
 * Snap
 */
fun AppCompatActivity.loadFragment(fragment: Fragment?, @IdRes into: Int): Boolean {
    if (fragment != null) {
        val fTransaction = supportFragmentManager.beginTransaction()
        fTransaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        fTransaction.disallowAddToBackStack()
            .replace(into, fragment)
            .commitAllowingStateLoss()
        return true
    }
    return false
}

fun Fragment.loadFragment(fragment: Fragment?, @IdRes into: Int): Boolean {
    if (fragment != null) {
        val fTransaction = childFragmentManager.beginTransaction()
        fTransaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        fTransaction
            .disallowAddToBackStack()
            .replace(into, fragment)
            .commitAllowingStateLoss()
        return true
    }
    return false
}