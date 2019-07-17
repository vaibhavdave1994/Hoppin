package com.hoppin.activity.hoopActivity

import android.os.Bundle
import android.support.v4.text.HtmlCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import com.hoppin.R
import com.hoppin.activity.hoopActivity.adapter.DetailGuestAdapter
import com.hoppin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_hoop_detail.*
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import java.io.File


class HoopDetailActivity : BaseActivity(),View.OnClickListener {

    lateinit var detailGuestAdapter: DetailGuestAdapter
    lateinit var description:String
    lateinit var see:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_hoop_detail)
        inItView()

    }

    fun inItView() {
        iv_back.setOnClickListener(this)
        rl_share.setOnClickListener(this)
        adapterData()
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard.."
        see = "<font color=\"#00A65B\"><U> <small>" + "See More" + " </small></U></font>"

        tv_description.text = HtmlCompat.fromHtml(description + "" + see,0)

    }

    fun adapterData() {
        detailGuestAdapter = DetailGuestAdapter(this)
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.layoutManager = mLayoutManager
        //addprop_recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.adapter = detailGuestAdapter
    }

    override fun onClick(p0: View) {

        when(p0.id){
            R.id.iv_back ->{

                onBackPressed()
            }
            R.id.rl_share ->{

                val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                val shareBodyText = "Check it out. Your message goes here"
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here")
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText)
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"))
            }

        }
    }


}
