package com.hoppin.base

/**
 * Created by Ravi Birla on 22,July,2019
 */
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hoppin.R
import kotlinx.android.synthetic.main.hoopdowndialog.*


class HoopPickerDialog : BaseBottomSheetDialog(), View.OnClickListener {
    val TAG = HoopPickerDialog::class.java.name
    var callBack: HoopPickerCallBack? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hoopdowndialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rl_edit.setOnClickListener(this)
        rl_remove.setOnClickListener(this)
        rl_share.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }

    companion object {
        @JvmStatic
        fun newInstance(baseFragment: BaseActivity) =
                HoopPickerDialog().apply {
                    arguments = Bundle().apply {
                        setOnTextListener(baseFragment)
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
            R.id.rl_edit -> {
                dismiss()
            }
            R.id.rl_remove -> {
                dismiss()
            }
            R.id.rl_share -> {
                dismiss()
            }
            R.id.tv_cancel -> {
                dismiss()
            }
        }

    }

    interface HoopPickerCallBack {
        fun textOnClick(type: String)
    }
}