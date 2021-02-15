package sen.com.abstraction.bases.adapter

import android.view.View
import com.nhaarman.mockitokotlin2.spy
import junit.framework.TestCase
import org.junit.Before
import sen.com.abstraction.R

/**
 * Created by korneliussendy on 2/8/21,
 * at 4:10 PM.
 * Snap
 */
class BaseAdapterTest : TestCase() {
    companion object {
        fun createAdapter() = object : BaseAdapter<Any>() {
            override fun mainLayout() = R.layout.simple_cell_empty
            override fun onBindView(v: View, item: Any, pos: Int) {}
        }
    }

    lateinit var adapter: BaseAdapter<Any>

    @Before
    override fun setUp() {
        super.setUp()
        adapter = spy(createAdapter())
    }

    fun testGetItems() {}

    fun testIsEmpty() {}

    fun testGetItem() {}

    fun testAddItem() {}

    fun testTestAddItem() {}

    fun testAddItems() {}

    fun testUpdateItem() {}

    fun testRemoveLast() {}

    fun testClear() {}

    fun testGetItemCount() {}

    fun testNotifyConfigChanged() {}

    fun testLoadingItemCount() {}

    fun testShowLoader() {}

    fun testHideLoader() {}

    fun testGetHeaderLayout() {}

    fun testSetHeaderLayout() {}

    fun testGetOnBindHeader() {}

    fun testGetSetupHeader() {}

    fun testSetSetupHeader() {}

    fun testShowHeader() {}

    fun testGetError() {}

    fun testSetError() {}

    fun testShowError() {}

    fun testHideError() {}

    fun testGetItemViewType() {}

    fun testGetItemLayout() {}

    fun testGetItemId() {}

    fun testOnCreateViewHolder() {}

    fun testOnBindViewHolder() {}

    fun testOnBindView() {}
}