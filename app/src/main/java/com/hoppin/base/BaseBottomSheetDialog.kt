package com.hoppin.base

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.View
import com.hoppin.Hoopin
import com.hoppin.data.AppDataManager

/**
 * Created by Ravi Birla on 26,June,2019
 */
open class BaseBottomSheetDialog : BottomSheetDialogFragment(), View.OnFocusChangeListener {
    var mActivity = BaseActivity()

    protected val dataManager: AppDataManager
        get() = Hoopin.getDataManager()


    fun getBaseActivity(): BaseActivity {
        return mActivity
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.mActivity = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun show(fragmentManager: FragmentManager, tag: String) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null) {
            transaction.remove(prevFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    fun dismissDialog(tag: String) {
        hideKeyboard()
        dismiss()
    }

    protected fun hideKeyboard() {
        mActivity.hideKeyboard()

    }

    protected fun setLoading(isLoading: Boolean) {
        mActivity.setLoading(isLoading)
    }

    /* protected fun setLoading(progressBar: ProgressBar, isLoading: Boolean) {
         if (mActivity != null) {
             mActivity!!.setLoading(progressBar, isLoading)
         }
     }*/

    override fun onFocusChange(v: View, hasFocus: Boolean) {
        if (!hasFocus) {
            hideKeyboard()
        }
    }

}