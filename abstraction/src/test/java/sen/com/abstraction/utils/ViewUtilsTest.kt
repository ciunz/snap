package sen.com.abstraction.utils

import android.view.View
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created by korneliussendy on 2/2/21,
 * at 9:04 PM.
 * Snap
 */
class ViewUtilsTest {
    private val view = mock(View::class.java)

    @Test
    fun goneIsCorrect() {
        ViewUtils.gone(view)
        verify(view, times(1)).visibility = View.GONE
        verifyNoMoreInteractions(view)
    }

    @Test
    fun visibleIsCorrect() {
        ViewUtils.visible(view)
        verify(view, times(1)).visibility = View.VISIBLE
        verifyNoMoreInteractions(view)
    }

    @Test
    fun invisibleIsCorrect() {
        ViewUtils.invisible(view)
        verify(view, times(1)).visibility = View.INVISIBLE
        verifyNoMoreInteractions(view)
    }

    @Test
    fun visibleOrGoneIsCorrect() {
        ViewUtils.visibleOrGone(true, view)
        verify(view, times(1)).visibility = View.VISIBLE

        ViewUtils.visibleOrGone(false, view)
        verify(view, times(1)).visibility = View.GONE
    }

    @Test
    fun visibleOrInvisibleIsCorrect() {
        ViewUtils.visibleOrInvisible(true, view)
        verify(view, times(1)).visibility = View.VISIBLE

        ViewUtils.visibleOrInvisible(false, view)
        verify(view, times(1)).visibility = View.INVISIBLE
    }

    @Test
    fun enableIsCorrect() {
        ViewUtils.enable(view)
        verify(view, times(1)).isEnabled = true
    }

    @Test
    fun disableIsCorrect() {
        ViewUtils.disable(view)
        verify(view, times(1)).isEnabled = false
    }

    @Test
    fun enableIfIsCorrect() {
        ViewUtils.enableIf(true, view)
        verify(view, times(1)).isEnabled = true

        ViewUtils.enableIf(false, view)
        verify(view, times(1)).isEnabled = false
    }

    @Test
    fun disableIfIsCorrect() {
        ViewUtils.disableIf(true, view)
        verify(view, times(1)).isEnabled = false

        ViewUtils.disableIf(false, view)
        verify(view, times(1)).isEnabled = true
    }
}