package com.hoppin.activity.hoopActivity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hoppin.R
import com.hoppin.activity.hoopActivity.adapter.PriviousHoopAdapter
import com.hoppin.activity.hoopActivity.model.PriviousHoopDataModel
import com.hoppin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_previous_create_hoop.*

class PreviousCreateHoopActivity : BaseActivity(), View.OnClickListener {

    lateinit var priviousHoopAdapter: PriviousHoopAdapter
    lateinit var arrayList: ArrayList<PriviousHoopDataModel>
    lateinit var priviousHoopDataModel: PriviousHoopDataModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_create_hoop)
        inItView()
        adapterData()
    }

    fun inItView() {
        iv_back.setOnClickListener(this)
        btn_done.setOnClickListener(this)
        arrayList = ArrayList()
        priviousHoopDataModel = PriviousHoopDataModel()
    }

    fun adapterData() {

        priviousHoopAdapter = PriviousHoopAdapter(this)
        val mLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = mLayoutManager
        //addprop_recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.adapter = priviousHoopAdapter
    }


    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
            R.id.btn_done -> {
                onBackPressed()
            }
        }

    }
}
