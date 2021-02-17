package sen.com.snap.ui.userProfile

import dagger.hilt.android.AndroidEntryPoint
import sen.com.abstraction.utils.binding.dataBinding
import sen.com.abstraction.utils.binding.viewBinding
import sen.com.core.base.CoreActivity
import sen.com.snap.R
import sen.com.snap.databinding.AMainBinding
import sen.com.snap.databinding.AUserProfileBinding

/**
 * Created by korneliussendy on 2/15/21,
 * at 2:19 PM.
 * Snap
 */

@AndroidEntryPoint
class AUserProfile : CoreActivity() {
    override fun contentView(): Int = R.layout.a_user_profile
    override fun showToolbar() = true
    private val view: AUserProfileBinding by viewBinding()
    override fun initView() {
    }
}