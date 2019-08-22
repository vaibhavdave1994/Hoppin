package com.hoppin.fragment.hoopinfragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hoppin.R
import com.hoppin.base.BaseFragment
import com.hoppin.fragment.adapter.HoopRequestAdapter
import kotlinx.android.synthetic.main.fragment_hoop_request.*

class HoopRequestFragment : BaseFragment() {

    lateinit var hoopRequestAdapter: HoopRequestAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hoop_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapterData()
    }

    fun adapterData() {
        hoopRequestAdapter = HoopRequestAdapter(context!!)
        val mLayoutManager = LinearLayoutManager(context!!)
        recycler_view.layoutManager = mLayoutManager
        recycler_view.adapter = hoopRequestAdapter


    }

    fun setTimer(){


    }
}
