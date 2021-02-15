package sen.com.abstraction.extentions

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by korneliussendy on 2/14/21,
 * at 11:51 AM.
 * Snap
 */

fun RecyclerView.setup(context: Context, adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = LinearLayoutManager(context)
    this.adapter = adapter
}