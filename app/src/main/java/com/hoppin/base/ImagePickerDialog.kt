package com.hoppin.base

/**
 * Created by Ravi Birla on 27,June,2019
 */
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hoppin.R
import kotlinx.android.synthetic.main.dialog_image_picker.*


class ImagePickerDialog : BaseBottomSheetDialog(), View.OnClickListener {
    val TAG = ImagePickerDialog::class.java.name
    var callBack: ImagePickerCallBack? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_image_picker, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rl_camera.setOnClickListener(this)
        rl_gallery.setOnClickListener(this)
        rl_cancel.setOnClickListener(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }

    companion object {
        @JvmStatic
        fun newInstance(baseActivity: BaseActivity) =
                ImagePickerDialog().apply {
                    arguments = Bundle().apply {
                        setOnTextListener(baseActivity)
                        // putString(TEXTVALUE, type)
                    }
                }


    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, TAG)
    }

    private fun setOnTextListener(callBack: BaseActivity) {
        this.callBack = callBack
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_camera -> {
                callBack?.textOnClick("Camera")
                dismiss()
            }
            R.id.rl_gallery -> {
                callBack?.textOnClick("Gallery")
                dismiss()
            }
            R.id.rl_cancel -> {
                dismiss()
            }
        }

    }

    interface ImagePickerCallBack {
        fun textOnClick(type: String)
    }
}