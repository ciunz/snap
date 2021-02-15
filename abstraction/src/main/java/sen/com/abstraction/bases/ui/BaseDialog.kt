package sen.com.abstraction.bases.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import sen.com.abstraction.R
import sen.com.abstraction.strategies.ImageLoaderStrategy
import sen.com.abstraction.utils.ViewUtils
import sen.com.abstraction.utils.onClick

/**
 * Created by korneliussendy on 2/7/21,
 * at 1:34 AM.
 * Snap
 */
abstract class BaseDialog(
    private val context: Context,
    private val inflater: LayoutInflater = LayoutInflater.from(context),
    private val imageLoader: ImageLoaderStrategy? = null
) {

    protected var dialog: AppCompatDialog? = null

    var mode = Mode.POPUP
        protected set
    var cancelable = true
        protected set
    var title = ""
        protected set
    var description = ""
        protected set
    var imageRes = 0
        protected set
    var imageUrl = ""
        protected set
    var buttonPositive = ""
        protected set
    var buttonNegative = ""
        protected set

    private var onCancel: (() -> Unit)? = null
    private var onNegative: (() -> Unit)? = null
    private var onPositive: (() -> Unit)? = null

    //FLAGS
    var showImageRes = false
        protected set
    var showImage = false
        protected set
    var showTitle = false
        protected set
    var showDescription = false
        protected set
    var showNegativeButton = false
        protected set
    var showPositiveButton = false
        protected set

    //OTHERS
    protected var disableDim = false

    enum class Mode {
        POPUP, BOTTOMSHEET
    }

    fun withMode(mode: Mode): BaseDialog {
        this.mode = mode
        return this
    }

    @LayoutRes
    abstract fun getLayout(mode: Mode): Int

    /**
     * All this function handle all basic requirement to create a dialog
     */
    fun withImageRes(@DrawableRes imageRes: Int): BaseDialog {
        showImageRes = true
        this.imageRes = imageRes
        return this
    }

    fun withImage(url: String): BaseDialog {
        showImage = true
        this.imageUrl = url
        return this
    }

    fun withTitleRes(@StringRes res: Int) = withTitle(context.getString(res))
    fun withTitle(title: String?): BaseDialog {
        showTitle = !title.isNullOrEmpty()
        this.title = title ?: ""
        return this
    }

    fun withDescriptionRes(@StringRes res: Int) = withDescription(context.getString(res))
    fun withDescription(description: String): BaseDialog {
        showDescription = true
        this.description = description
        return this
    }

    fun withPositiveButtonRes(@StringRes stringRes: Int, onClick: (() -> Unit)? = null) =
        withPositiveButton(context.getString(stringRes), onClick)

    fun withPositiveButton(text: String, onClick: (() -> Unit)? = null): BaseDialog {
        showPositiveButton = true
        this.buttonPositive = text
        this.onPositive = onClick
        return this
    }

    fun withNegativeButtonRes(@StringRes stringRes: Int, onClick: (() -> Unit)? = null) =
        withNegativeButton(context.getString(stringRes), onClick)

    fun withNegativeButton(text: String, onClick: (() -> Unit)? = null): BaseDialog {
        showNegativeButton = true
        this.buttonNegative = text
        this.onNegative = onClick
        return this
    }

    private fun generateDialog(contentView: View): AppCompatDialog = when (mode) {
        Mode.POPUP -> {
            val builder = AlertDialog.Builder(context)
            builder.setCancelable(cancelable)
            dialog = builder.setView(contentView).create()
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog as AlertDialog
        }

        Mode.BOTTOMSHEET -> {
            dialog = BottomSheetDialog(context)
            dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.setContentView(contentView)
            dialog as BottomSheetDialog
        }
    }

    /**
     * inflating the view
     * will setup layout according to [Mode] given
     * implement [cancelable] default true, add [onCancel] if provided
     * invoke [setupBasicElements], [setupButton], and [onViewCreated]
     * @return [AppCompatDialog] to be further modified if necessary.
     */
    @CallSuper
    fun show(): AppCompatDialog {
        val v = inflater.inflate(getLayout(mode), null)
        dialog = generateDialog(v)
        dialog?.setCancelable(cancelable)
        dialog?.setCanceledOnTouchOutside(cancelable)
        dialog?.setOnCancelListener { onCancel?.invoke() }
        if (disableDim) dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        /**
         * this is minimal id for custom layout
         */
        val ivClose: ImageView? = v.findViewById(R.id.ivClose)
        val vTop: View? = v.findViewById(R.id.vTop)
        val ivImage: ImageView? = v.findViewById(R.id.ivImage)
        val tvTitle: TextView? = v.findViewById(R.id.tvTitle)
        val tvDescription: TextView? = v.findViewById(R.id.tvDescription)
        val btnNegative: Button? = v.findViewById(R.id.btnNegative)
        val btnPositive: Button? = v.findViewById(R.id.btnPositive)

        setupBasicElements(tvTitle, tvDescription, ivImage)
        setupButton(btnPositive, btnNegative)

        ViewUtils.visibleOrGone(showTitle || cancelable, vTop)
        ViewUtils.visibleOrInvisible(cancelable, ivClose)
        ivClose?.onClick { dialog?.dismiss() }

        onViewCreated(v)
        dialog?.show()
        return dialog!!
    }

    /**
     * Handle your custom unique layout here
     */
    abstract fun onViewCreated(view: View)

    /**
     * setting up [tvTitle], [tvDescription] and [ivImage] if provided
     * will load image with [ImageLoaderStrategy] strategy
     * [ivImage] will be hidden if [imageLoader] not exist
     * or both [showImageRes] and [showImage] are false
     */
    private fun setupBasicElements(
        tvTitle: TextView?,
        tvDescription: TextView?,
        ivImage: ImageView?
    ) {
        imageLoader?.let {
            ViewUtils.visible(ivImage)
            when {
                showImageRes -> it.load(ivImage, imageRes)
                showImage -> it.load(ivImage, imageUrl)
                else -> ViewUtils.gone(ivImage)
            }
        } ?: ViewUtils.gone(ivImage)

        tvTitle?.text = title
        ViewUtils.visibleOrGone(showTitle, tvTitle)

        ViewUtils.visibleOrGone(showDescription, tvDescription)
        tvDescription?.text = description
    }

    /**
     * setting up [Button]'s for bottom action
     * if [showNegativeButton] [btnNegative] will be visible, applied text [buttonNegative],
     * implement [View.OnClickListener] that will invoke [onNegative] and cancel [AlertDialog]
     *
     * if [showPositiveButton] [btnPositive] will be visible, applied text [buttonPositive],
     * implement [View.OnClickListener] that will invoke [onPositive] and cancel [AlertDialog]
     */
    private fun setupButton(btnPositive: Button?, btnNegative: Button?) {
        ViewUtils.visibleOrGone(showNegativeButton, btnNegative)

        if (showNegativeButton) {
            btnNegative?.text = buttonNegative
            btnNegative?.setOnClickListener {
                onNegative?.invoke()
                dialog?.cancel()
            }
        }

        ViewUtils.visibleOrGone(showPositiveButton, btnPositive)

        if (showPositiveButton) {
            btnPositive?.text = buttonPositive
            btnPositive?.setOnClickListener {
                onPositive?.invoke()
                dialog?.cancel()
            }
        }
    }
}