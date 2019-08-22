package com.hoppin.activity.hoopActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.hoppin.R
import com.hoppin.activity.hoopActivity.adapter.HoopMemoryAdapter
import kotlinx.android.synthetic.main.activity_previous_create_hoop.*


class HoopMemoryActivity : AppCompatActivity() {

    lateinit var hoopMemoryAdapter: HoopMemoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hoop_memory)
        inItView()
    }

    fun inItView() {
        adapterData()
    }

    fun adapterData() {

        hoopMemoryAdapter = HoopMemoryAdapter(this)
        val gridLayoutManager = GridLayoutManager(applicationContext, 3, LinearLayoutManager.VERTICAL, false)
        recycler_view.layoutManager = gridLayoutManager
        recycler_view.adapter = hoopMemoryAdapter
    }
}
