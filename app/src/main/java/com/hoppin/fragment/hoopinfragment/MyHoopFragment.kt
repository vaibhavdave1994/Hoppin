package com.hoppin.fragment.hoopinfragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hoppin.R
import com.hoppin.base.BaseFragment
import com.hoppin.base.HoopPickerDialog
import com.hoppin.fragment.adapter.MyHoopAdapter
import kotlinx.android.synthetic.main.fragment_my_hoop.*


class MyHoopFragment : BaseFragment() {
    lateinit var myHoopAdapter: MyHoopAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_hoop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapterData()
    }

    fun adapterData() {
        myHoopAdapter = MyHoopAdapter(context!!, object : MyHoopAdapter.HoopDialogListener {

            override fun dialog(position: Int) {
                HoopPickerDialog.newInstance(mActivity!!).show(childFragmentManager)
            }
        })
        val mLayoutManager = LinearLayoutManager(context!!)
        recycler_view.layoutManager = mLayoutManager
        recycler_view.adapter = myHoopAdapter
    }
}
