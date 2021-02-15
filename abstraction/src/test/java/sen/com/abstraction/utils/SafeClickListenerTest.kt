package sen.com.abstraction.utils

import android.view.View
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created by korneliussendy on 2/3/21,
 * at 12:05 AM.
 * Snap
 */

class SafeClickListenerTest {

    private val view = mock(View::class.java)

    @Test
    fun onClickIsCorrect() {
        val onSafeCLick: (View) -> Unit = mock()
        val safeClickListener = SafeClickListener(1000, onSafeCLick)
        val view = mock(View::class.java)

        safeClickListener.onClick(view, 10000L)
        argumentCaptor<View>().apply {
            verify(onSafeCLick, times(1)).invoke(capture())
        }

        safeClickListener.onClick(view, 10)
        argumentCaptor<View>().apply {
            verify(onSafeCLick, times(1)).invoke(capture())
        }
    }

    @Test
    fun removeOnClickIsCorrect() {
        view.removeOnClick()
        verify(view, times(1)).setOnClickListener(null)
    }
}