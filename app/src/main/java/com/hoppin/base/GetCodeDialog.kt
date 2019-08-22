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
import com.hoppin.activity.ui.authantication.otpmessage.PhoneVarificationActivity
import kotlinx.android.synthetic.main.getcodedialog.*


class GetCodeDialog : BaseBottomSheetDialog(), View.OnClickListener {
    val TAG = GetCodeDialog::class.java.name
    var callBack: GetCodeDialogCallBack? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.getcodedialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rl_resend.setOnClickListener(this)
        rl_editnumber.setOnClickListener(this)
        tv_cancel.setOnClickListener(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

    }

    companion object {
        @JvmStatic
        fun newInstance(baseFragment: PhoneVarificationActivity) =
                GetCodeDialog().apply {
                    arguments = Bundle().apply {
                        setOnTextListener(baseFragment)
                        // putString(TEXTVALUE, type)
                    }
                }


    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, TAG)
    }


    private fun setOnTextListener(callBack: PhoneVarificationActivity) {
        this.callBack = callBack
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rl_resend -> {
               callBack?.textcode("send")
                dismiss()
            }
            R.id.rl_editnumber -> {

                callBack?.textcode("edit")
                dismiss()
            }
            R.id.tv_cancel -> {
                dismiss()
            }
        }

    }

    interface GetCodeDialogCallBack {
        fun textcode(type: String)
    }
}