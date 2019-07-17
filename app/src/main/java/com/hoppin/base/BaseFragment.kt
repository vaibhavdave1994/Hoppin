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
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ravi Birla on 26,June,2019
 */
open class BaseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var tmpUri: Uri? = null
    private var param1: String? = null
    private var param2: String? = null
    private val ARG_PARAM1 = "param1"
    private val ARG_PARAM2 = "param2"
    //private var dataManager : AppDataManager? = null
    protected var mActivity: BaseActivity? = null


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


    /*fun getDataManager() : AppDataManager{
         dataManager = AppDataManager.getAppDataManager(activity!!.applicationContext)
        return  dataManager as AppDataManager

    }*/


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

    fun getImagePickerDialog() {
        ImagePickerDialog.newInstance(BaseActivity()).show(childFragmentManager)
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                BaseFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)

                    }
                }
    }

  /*  override fun textOnClick(type: String) {
        if (type.equals("Camera")) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            /////Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(context!!.packageManager) != null) {
                //Create the File where the photo should go
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (ex: IOException) {
                    Log.d(TAG, ex.message)
                }
                if (photoFile != null) {
                    tmpUri = FileProvider.getUriForFile(
                            context!!,
                            context!!.packageName + ".fileprovider",
                            getTemporalFile(BaseActivity())
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tmpUri)
                    startActivityForResult(takePictureIntent, Constant.CAMERA)
                }
            }
        } else if (type.equals("Gallery")) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, Constant.GALLERY)

        } else {

        }


    }*/

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

    /*fun getTemporalFile(baseActivity: BaseActivity): File {
        return File(baseActivity.getExternalCacheDir(), "tmp.jpg")
    }*/


}