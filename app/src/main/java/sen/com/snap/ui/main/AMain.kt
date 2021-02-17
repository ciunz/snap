package sen.com.snap.ui.main

import dagger.hilt.android.AndroidEntryPoint
import sen.com.abstraction.extentions.startActivity
import sen.com.abstraction.utils.binding.viewBinding
import sen.com.abstraction.utils.onClick
import sen.com.core.Router
import sen.com.core.base.CoreActivity
import sen.com.snap.R
import sen.com.snap.databinding.AMainBinding

@AndroidEntryPoint
class AMain : CoreActivity() {
    override fun contentView() = R.layout.a_main
    override fun showToolbar() = true

    private val view: AMainBinding by viewBinding()

    override fun initView() {
        setTitle("Snap")
        view.btnProfile.onClick {
            startActivity(Router.USER_PROFILE)
        }
    }
}