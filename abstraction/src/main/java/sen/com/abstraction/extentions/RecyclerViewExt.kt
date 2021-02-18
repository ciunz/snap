package sen.com.abstraction.extentions

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by korneliussendy on 2/14/21,
 * at 11:51 AM.
 * Snap
 */

fun RecyclerView.setup(
    context: Context,
    adapter: RecyclerView.Adapter<*>,
    @RecyclerView.Orientation orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) {
    this.layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
    this.adapter = adapter
}

fun RecyclerView.setupHorizontal(context: Context, adapter: RecyclerView.Adapter<*>) =
    this.setup(context, adapter, RecyclerView.HORIZONTAL)