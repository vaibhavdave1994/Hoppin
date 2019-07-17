package com.hoppin.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v4.text.HtmlCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.doviesfitness.utils.ImageRotator
import com.hoppin.R
import com.hoppin.activity.hoopActivity.HoopDetailActivity
import com.hoppin.activity.hoopActivity.PreviousCreateHoopActivity
import com.hoppin.activity.hoopActivity.SelcetGuestActivity
import com.hoppin.activity.hoopActivity.adapter.SelectGuestAdapter
import com.hoppin.activity.ui.tabactivity.TabActivity
import com.hoppin.base.BaseFragment
import com.hoppin.base.Constant
import com.hoppin.fragment.adapter.HoopGuestAdapter
import com.hoppin.helper.Utility
import kotlinx.android.synthetic.main.fragment_create_hoop.*
import java.io.File
import java.util.*


class CreateHoopFragment : BaseFragment(), View.OnClickListener, Utility.OnDateSelectedListener, Utility.OnOptionDateSelectedListener {

    private var userImageFile: File? = null
    lateinit var utility: Utility
    lateinit var intent: Intent
    lateinit var description: String
    lateinit var option: String
    private var hoopGuestAdapter: HoopGuestAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_hoop, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as TabActivity).updateStatusBarColor("#ffffff")
        inItView()
        adapterData()
    }

    fun inItView() {
        utility = Utility()
        rl_startdate.setOnClickListener(this)
        rl_optiondate.setOnClickListener(this)
        rl_hoopprofile.setOnClickListener(this)
        btn_pasthoop.setOnClickListener(this)
        btn_create.setOnClickListener(this)
        ll_invited.setOnClickListener(this)

        description = "<font color=\"#828282\"> " + getString(R.string.description) + "<> </font>"
        option = "<font color=\"#626262\"> <small>" + getString(R.string.option) + " <small></font>"

        et_description.hint = HtmlCompat.fromHtml(description + " " + option,0)


    }

    fun adapterData() {
        hoopGuestAdapter = HoopGuestAdapter(context!!)
        val mLayoutManager = LinearLayoutManager(context!!, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.layoutManager = mLayoutManager
        //addprop_recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.adapter = hoopGuestAdapter

    }

    override fun onClick(p0: View) {

        when (p0.id) {
            R.id.rl_startdate -> {
                utility.startDateMathod(activity!!, this@CreateHoopFragment)
            }
            R.id.rl_optiondate -> {
                utility.optionDateMathod(activity!!, this@CreateHoopFragment)
            }
            R.id.rl_hoopprofile -> {
                mActivity?.getImagePickerDialog()
                //getImagePickerDialog()
            }
            R.id.btn_pasthoop -> {
                intent = Intent(context, PreviousCreateHoopActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_create -> {
                intent = Intent(context, HoopDetailActivity::class.java)
                startActivity(intent)
            }
            R.id.ll_invited -> {
                intent = Intent(context, SelcetGuestActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDateSelect(formatedDate: String) {
        tv_date.text = formatedDate
    }

    override fun onOptionDateSelect(formatedDate: String) {
        tv_optiondate.text = formatedDate
    }

    /*Image picker code*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            tmpUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(mActivity!!.contentResolver, tmpUri)
            userImageFile = Constant.savebitmap(mActivity!!, bitmap, UUID.randomUUID().toString() + ".jpg")
            Glide.with(iv_profile.context).load(bitmap).into(iv_profile)
            iv_profile.borderWidth = 0
        } else if (requestCode == Constant.CAMERA && resultCode == Activity.RESULT_OK) {
            val imageFile = mActivity?.getTemporalFile(mActivity!!)
            val photoURI = Uri.fromFile(imageFile)
            var bm = Constant.getImageResized(mActivity!!, photoURI) ///Image resizer
            val rotation = ImageRotator.getRotation(mActivity!!, photoURI, true)
            bm = ImageRotator.rotate(bm, rotation)
            val profileImagefile = File(mActivity!!.externalCacheDir, UUID.randomUUID().toString() + ".jpg")
            tmpUri = FileProvider.getUriForFile(
                    mActivity!!, mActivity!!.applicationContext.packageName + ".fileprovider",
                    profileImagefile
            )
            userImageFile = Constant.savebitmap(mActivity!!, bm, StringBuilder().append(UUID.randomUUID()).append(".jpg").toString())
            iv_profile.borderWidth = 0
            iv_profile.setImageBitmap(bm)
        }
    }
}
