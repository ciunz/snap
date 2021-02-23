package sen.com.snap.ui.demoFragment

import sen.com.abstraction.utils.binding.viewBinding
import sen.com.core.base.CoreFragment
import sen.com.snap.R
import sen.com.snap.databinding.FDemoBinding

/**
 * Created by korneliussendy on 2/23/21,
 * at 8:49 AM.
 * Snap
 */
class FDemo : CoreFragment() {
    override fun contentView() = R.layout.f_demo
    private val view: FDemoBinding by viewBinding()

    override fun initView() {

    }
}