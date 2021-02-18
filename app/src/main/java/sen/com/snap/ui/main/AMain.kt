package sen.com.snap.ui.main

import dagger.hilt.android.AndroidEntryPoint
import sen.com.abstraction.extentions.routeOnClick
import sen.com.abstraction.utils.binding.viewBinding
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
        view.btnProfile.routeOnClick(Router.USER_PROFILE)
    }
}