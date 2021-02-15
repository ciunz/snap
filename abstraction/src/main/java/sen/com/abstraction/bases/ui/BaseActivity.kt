package sen.com.abstraction.bases.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import sen.com.abstraction.BuildConfig
import sen.com.abstraction.R
import sen.com.abstraction.bases.data.BaseViewModel
import sen.com.abstraction.bases.data.model.Event
import sen.com.abstraction.databinding.BaseActivityBinding
import sen.com.abstraction.extentions.*
import sen.com.abstraction.utils.ConnectionLiveData
import sen.com.abstraction.utils.ViewUtils.gone
import sen.com.abstraction.utils.ViewUtils.visible
import sen.com.abstraction.utils.ViewUtils.visibleOrGone
import sen.com.abstraction.utils.ViewUtils.visibleOrInvisible
import sen.com.abstraction.utils.onClick

/**
 * Created by korneliussendy on 25/09/20,
 * at 11.04.
 * Snap
 */
abstract class BaseActivity : AppCompatActivity() {

    lateinit var viewContent: View private set

    @LayoutRes
    abstract fun contentView(): Int

    /**
     * will be called when setup completed
     * implement each activity views and data load here
     */
    abstract fun initView()

    private var baseBinding: BaseActivityBinding? = null

    //CONTENT
    private var v: View? = null
    private var vBaseLoader: ViewStub? = null
    private var viewLoaderInflated = false

    /**
     * handle network connection update
     */
    protected open fun onConnectionUpdated(isConnected: Boolean) {
        visibleOrGone(!isConnected, findViewById(R.id.tvConnection))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        if (showToolbar()) setupToolbar()
        bindImmediate()
        initView()
    }

    /**
     * initialization of bases view such as toolbar, content, base loader
     */
    @SuppressLint("SetTextI18n")
    private fun setupView() {
        baseBinding = DataBindingUtil.setContentView(this, R.layout.base_activity)
        if (supportActionBar == null) baseBinding?.baseToolbar?.let { setSupportActionBar(it) }
        baseBinding?.basePageLoader?.let { vBaseLoader = it.viewStub }
        baseBinding?.tvPageName?.text = "DEV : ${this.javaClass.simpleName}"
        baseBinding?.vBaseContent?.let { viewContent = it }

        ConnectionLiveData(this).observe(this) { onConnectionUpdated(it) }
    }

    /**
     * use this to inflate desired layout
     */
    protected open fun <T : ViewDataBinding> dataBind(@LayoutRes resId: Int): T =
        DataBindingUtil.inflate(layoutInflater, resId, viewContent as ViewGroup, true)

    fun <T : ViewDataBinding> dataBind(): T = dataBind(contentView())

    protected open fun bindImmediate(): ViewDataBinding? = null

    /**
     * TOOLBAR SECTION
     * do setup toolbar here
     */
    private var holderToolbar: ViewStub? = null
    private var toolbar: RelativeLayout? = null
    private var tvTitle: TextView? = null
    private var vSpacer: View? = null
    private var tvSubTitle: TextView? = null
    private var ivLeft: ImageView? = null
    private var ivRight: ImageView? = null
    private var ivSecondRight: ImageView? = null
    private var btnRight: Button? = null

    open fun showToolbar(): Boolean = false
    open fun showLeftIcon(): Boolean = true
    open fun showRightIcon(): Boolean = false
    open fun showSecondRightIcon(): Boolean = false

    @ColorRes
    open fun tintColor() = R.color.white

    /*************************************
     * TOOLBAR
     *************************************/

    open fun toolbarElevation(): Int? = null

    @ColorRes
    open fun toolbarColor() = R.color.toolbar_color

    @DrawableRes
    open fun toolbarDrawable(): Int? = null

    fun setToolbarDrawable(@DrawableRes drawable: Int?) =
        drawable?.let { toolbar?.background = ContextCompat.getDrawable(this, it) }

    fun setTitle(title: String) {
        tvTitle?.text = title
    }

    fun setSubTitle(title: String) {
        tvSubTitle?.text = title
    }

    override fun setTitle(title: CharSequence?) {
        tvTitle?.text = title
    }

    fun setSubTitle(subtitle: CharSequence?) {
        tvSubTitle?.text = subtitle
    }

    /*************************************
     * RIGHT ICON
     *************************************/

    @DrawableRes
    open fun resRightIcon(): Int = R.drawable.ic_next

    @DrawableRes
    open fun resSecondRightIcon(): Int = R.drawable.ic_next

    open fun showRightIcon(@DrawableRes imageRes: Int, onClick: ((v: View) -> Unit)? = null) {
        ivRight?.let {
            visible(it)
            it.setImageDrawable(ContextCompat.getDrawable(this, imageRes))
            onClick?.let { v -> it.onClick(v) }
        }
    }

    open fun showSecondRightIcon(@DrawableRes imageRes: Int, onClick: ((v: View) -> Unit)? = null) {
        ivSecondRight?.let {
            visible(ivSecondRight)
            it.setImageDrawable(ContextCompat.getDrawable(this, imageRes))
            onClick?.let { v -> it.onClick(v) }
        }
    }

    fun getRightIconView(): ImageView? = findViewById(R.id.ivBaseRight)
    fun hideRightIcon() = gone(ivRight)

    fun getSecondRightIconView(): ImageView? = findViewById(R.id.ivBaseRight2)
    fun hideSecondRightIcon() = gone(ivSecondRight)

    protected fun onRightIconClicked(onClick: (v: View) -> Unit) =
        ivRight?.setOnClickListener { onClick(it) }

    /*************************************
     * LEFT ICON
     *************************************/

    @DrawableRes
    open fun resLeftIcon(): Int = R.drawable.ic_back
    fun getLeftIconView(): ImageView? = ivLeft
    protected fun onLeftIconClicked(onClick: (v: View) -> Unit) =
        ivLeft?.setOnClickListener { onClick(it) }

    open fun showLeftIcon(@DrawableRes imageRes: Int, onClick: ((v: View) -> Unit)? = null) {
        ivLeft?.let {
            visible(it)
            it.setImageDrawable(ContextCompat.getDrawable(this, imageRes))
            onClick?.let { v -> it.onClick(v) }
        }
    }

    /*************************************
     * RIGHT BUTTON
     *************************************/

    open fun showRightButton(text: String?, @ColorRes id: Int, onClick: (v: View) -> Unit) {
        visible(btnRight)
        btnRight?.text = text
        btnRight?.setTextColor(ContextCompat.getColor(this, id))
        btnRight?.onClick(onClick)
    }

    open fun showRightButton(@StringRes text: Int, @ColorRes id: Int, onClick: (v: View) -> Unit) =
        showRightButton(getString(text), id, onClick)

    open fun showRightButton(@StringRes text: Int, onClick: (v: View) -> Unit) =
        showRightButton(getString(text), R.color.white, onClick)

    open fun hideRightButton() = gone(btnRight)

    //SETUP

    private fun setupToolbar() {
        val v = baseBinding?.holderToolbar?.viewStub?.inflate()
        toolbar = v?.findViewById(R.id.baseToolbar)
        tvTitle = v?.findViewById(R.id.tvBaseTitle)
        vSpacer = v?.findViewById(R.id.vBaseSpacer)
        tvSubTitle = v?.findViewById(R.id.tvBaseSubTitle)
        ivLeft = v?.findViewById(R.id.ivBaseLeft)
        ivRight = v?.findViewById(R.id.ivBaseRight)
        btnRight = v?.findViewById(R.id.btnBaseRight)

        visibleOrGone(showToolbar(), toolbar, baseBinding?.baseToolbar)
        visibleOrInvisible(showLeftIcon(), ivLeft)
        visibleOrInvisible(showRightIcon(), ivRight)

        ivLeft?.setImageDrawable(ContextCompat.getDrawable(this, resLeftIcon()))
        ivRight?.setImageDrawable(ContextCompat.getDrawable(this, resRightIcon()))
        toolbarDrawable()?.let {
            toolbar?.background = ContextCompat.getDrawable(this, toolbarDrawable()!!)
        } ?: toolbar?.setBackgroundColor(ContextCompat.getColor(this, toolbarColor()))

        tvTitle?.setTextColor(ContextCompat.getColor(this, tintColor()))
        tvSubTitle?.setTextColor(ContextCompat.getColor(this, tintColor()))
        ivLeft?.updateTint(tintColor())
        ivRight?.updateTint(tintColor())
        onLeftIconClicked { onBackPressed() }

        visibleOrGone(BuildConfig.DEBUG, baseBinding?.tvPageName)
    }

    /*************************************
     * EVENT & LOADING LISTENER
     * receive event and handle
     **************************************/

    protected open var onLoading: ((loading: Boolean) -> Unit)? = null
    protected open fun defaultLoading() = false
    protected fun setMainViewModel(vm: BaseViewModel?) {
        if (defaultLoading()) vm?.loading?.observe(this, {
            onLoading?.invoke(it)
            //HANDLE LOADING HERE
            if (it) {
                hideKeyboard()
                toast("TODO: HANDLE LOADING")
            }
        })

        vm?.event?.observe(this, { event ->
            log("Incoming Event ${event.status.name}")
            when (event.status) {
                Event.Status.ROUTE ->
                    event.getContentIfNotHandled<String>()?.let {
                        hideKeyboard()
                        startActivity(it)
                    }
                Event.Status.ROUTE_FINISH ->
                    event.getContentIfNotHandled<String>()?.let {
                        hideKeyboard()
                        startActivity(it, true)
                    }
                Event.Status.ROUTE_CLEAR ->
                    event.getContentIfNotHandled<String>()?.let {
                        hideKeyboard()
                        startActivity(it)
                        finishAffinity()
                    }
                Event.Status.ERROR -> event.getContentIfNotHandled<Throwable>()
                Event.Status.NORMAL -> log("NORMAL")
                Event.Status.NORMAL_ALERT -> event.getContentIfNotHandled<String>()
                    ?.let { toast(it) }
                Event.Status.FATAL_ALERT -> log("FATAL_ALERT")
            }
        })
    }
}