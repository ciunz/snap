package sen.com.abstraction.bases.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import sen.com.abstraction.databinding.BaseFragmentBinding

/**
 * Created by korneliussendy on 25/09/20,
 * at 11.04.
 * Snap
 */
abstract class BaseFragment : Fragment() {

    private var baseBinding: BaseFragmentBinding? = null
    lateinit var viewContent: View private set

    @LayoutRes
    abstract fun contentView(): Int

    /**
     * will be called when setup completed
     * implement each fragment views and data load here
     */
    abstract fun initView()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseBinding = BaseFragmentBinding.inflate(inflater, container, false)
        return baseBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        baseBinding?.vBaseFragmentContent?.let {
            if (!it.isInflated) it.viewStub?.let { vs ->
                vs.layoutResource = contentView()
                viewContent = vs.inflate()
            }
        }
        initView()
    }

    /**
     * use this to inflate desired layout
     */
    open fun <T : ViewDataBinding> dataBind(@LayoutRes resId: Int): T =
        DataBindingUtil.inflate(layoutInflater, resId, viewContent as ViewGroup, true)

    fun <T : ViewDataBinding> dataBind(): T = dataBind(contentView())

    override fun onDestroyView() {
        super.onDestroyView()
        baseBinding = null
    }
}