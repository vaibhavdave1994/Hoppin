package com.hoppin.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hoppin.base.Constant.Companion.TAG
import com.hoppin.data.AppDataManager
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ravi Birla on 26,June,2019
 */
open class BaseFragment : Fragment(),HoopPickerDialog.HoopPickerCallBack {
    override fun textOnClick(type: String) {

    }

    // TODO: Rename and change types of parameters
    var tmpUri: Uri? = null
    private var param1: String? = null
    private var param2: String? = null
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"
    //private var dataManager : AppDataManager? = null
     var mActivity: BaseActivity? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(activity).apply {
            setText(" ")
        }
    }


    private lateinit var dataManager: AppDataManager

    fun getDataManager() : AppDataManager {
         dataManager = AppDataManager.getAppDataManager(activity!!.applicationContext)
        return  dataManager as AppDataManager

    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mActivity = context
        }
    }

    protected fun getBaseActivity(): BaseActivity? {
        return mActivity
    }

    fun setLoading(isload: Boolean) {
        mActivity?.setLoading(isload)
    }

    fun getHoopPickerDialog() {
        //HoopPickerDialog.newInstance(this).show(childFragmentManager)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                BaseFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)

                    }
                }
    }


    @SuppressLint("SimpleDateFormat")
    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context!!.externalCacheDir
        return File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )

    }




}