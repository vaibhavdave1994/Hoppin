package com.hoppin.activity.hoopActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hoppin.R
import com.hoppin.activity.hoopActivity.adapter.SelectGuestAdapter
import com.hoppin.activity.hoopActivity.model.PriviousHoopDataModel
import kotlinx.android.synthetic.main.activity_previous_create_hoop.*

class SelcetGuestActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var selectGuestAdapter: SelectGuestAdapter
    lateinit var arrayList: ArrayList<PriviousHoopDataModel>
    lateinit var priviousHoopDataModel: PriviousHoopDataModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selcet_guest)
        inItView()
        adapterData()
    }

    fun inItView() {
        iv_back.setOnClickListener(this)
        arrayList = ArrayList()
        priviousHoopDataModel = PriviousHoopDataModel()
    }

    fun adapterData() {

      val value =  ArrayList<Boolean>()
        for (i in 1..10){
            value.add(true)
        }

        selectGuestAdapter = SelectGuestAdapter(this, value)
        val mLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = mLayoutManager
        //addprop_recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.adapter = selectGuestAdapter
    }


    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.iv_back -> {
                onBackPressed()
            }

        }

    }
}
