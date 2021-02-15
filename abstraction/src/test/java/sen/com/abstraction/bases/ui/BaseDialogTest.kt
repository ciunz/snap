package sen.com.abstraction.bases.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import sen.com.abstraction.R


/**
 * Created by korneliussendy on 2/7/21,
 * at 2:08 PM.
 * Snap
 */
class BaseDialogTest : TestCase() {

    companion object {
        private val context = mock(Context::class.java)
        private val inflater = mock(LayoutInflater::class.java)
        private val anyStringRes = R.string.ANY
        const val anyString = "Any String"
        const val imageRes = 888

        private val layoutPopup = R.layout.base_dialog_popup
        private val layoutBottomSheet = R.layout.base_dialog_bottom_sheet

        fun createDialog(): BaseDialog = object : BaseDialog(context, inflater) {
            override fun getLayout(mode: Mode) = when (mode) {
                Mode.POPUP -> layoutPopup
                Mode.BOTTOMSHEET -> layoutBottomSheet
            }

            override fun onViewCreated(view: View) {

            }
        }
    }

    lateinit var dialog: BaseDialog

    @Before
    override fun setUp() {
        super.setUp()
        `when`(context.getString(anyStringRes)).thenReturn(anyString)
        dialog = spy(createDialog())
    }

    @Test
    fun shouldReturnDialog() {
        val dialog = createDialog()
        assertEquals(dialog.withTitle("Title"), dialog)
        assertEquals(dialog.withDescription("Description"), dialog)
        assertEquals(dialog.withNegativeButton("Negative Button"), dialog)
        assertEquals(dialog.withPositiveButton("Positive Button"), dialog)
        assertEquals(dialog.withMode(BaseDialog.Mode.BOTTOMSHEET), dialog)
    }

    fun testWithMode() {
        val mockMode = BaseDialog.Mode.BOTTOMSHEET
        `when`(dialog.mode).thenReturn(mockMode)
        dialog.withMode(mockMode)
        assertEquals(dialog.mode, mockMode)
    }

    fun testWithTitle() {
        `when`(dialog.title).thenReturn(anyString)
        // should return dialog
        assertEquals(dialog.withTitle(anyString), dialog)
        assertEquals(dialog.title, anyString)
        assert(dialog.showTitle == anyString.isNotEmpty())
    }

    fun testWithTitleRes() {
        `when`(dialog.title).thenReturn(anyString)
        // should return dialog
        assertEquals(dialog.withTitleRes(anyStringRes), dialog)
        assertEquals(dialog.title, anyString)
        verify(dialog, times(1)).withTitle(anyString)
    }

    fun testWithDescription() {
        `when`(dialog.description).thenReturn(anyString)
        // should return dialog
        assertEquals(dialog.withDescription(anyString), dialog)
        assertEquals(dialog.description, anyString)
        assert(dialog.showDescription == anyString.isNotEmpty())
    }

    fun testWithDescriptionRes() {
        `when`(dialog.description).thenReturn(anyString)
        // should return dialog
        assertEquals(dialog.withDescriptionRes(anyStringRes), dialog)
        assertEquals(dialog.description, anyString)
        verify(dialog, times(1)).withDescription(anyString)
    }

    fun testWithImageRes() {
        val mockImageRes: Int = 12
        `when`(dialog.imageRes).thenReturn(mockImageRes)
        // should return dialog
        assertEquals(dialog.withImageRes(mockImageRes), dialog)
        assertEquals(dialog.imageRes, mockImageRes)
        assert(dialog.showImageRes)
    }

    fun testWithImage() {
        `when`(dialog.imageUrl).thenReturn(anyString)
        // should return dialog
        assertEquals(dialog.withImage(anyString), dialog)
        assertEquals(dialog.imageUrl, anyString)
        assert(dialog.showImage == anyString.isNotEmpty())
    }

    fun testWithPositiveButtonRes() {
        `when`(dialog.buttonPositive).thenReturn(anyString)
        // should return dialog
        assertEquals(dialog.withPositiveButtonRes(anyStringRes), dialog)
        assertEquals(dialog.buttonPositive, anyString)
        verify(dialog, times(1)).withPositiveButton(anyString)
    }

    fun testWithPositiveButton() {
        `when`(dialog.buttonPositive).thenReturn(anyString)
        // should return dialog
        assertEquals(dialog.withPositiveButton(anyString), dialog)
        assertEquals(dialog.buttonPositive, anyString)
        assert(dialog.showPositiveButton)
    }

    fun testWithNegativeButtonRes() {
        `when`(dialog.buttonNegative).thenReturn(anyString)
        // should return dialog
        assertEquals(dialog.withNegativeButtonRes(anyStringRes), dialog)
        assertEquals(dialog.buttonNegative, anyString)
        verify(dialog, times(1)).withNegativeButton(anyString)
    }

    fun testWithNegativeButton() {
        `when`(dialog.buttonNegative).thenReturn(anyString)
        // should return dialog
        assertEquals(dialog.withNegativeButton(anyString), dialog)
        assertEquals(dialog.buttonNegative, anyString)
        assert(dialog.showNegativeButton)
    }

    fun testGetLayout() {
        assertEquals(dialog.getLayout(BaseDialog.Mode.BOTTOMSHEET), layoutBottomSheet)
        assertEquals(dialog.getLayout(BaseDialog.Mode.POPUP), layoutPopup)
    }

    fun testShow() {}

    fun testOnViewCreated() {}
}