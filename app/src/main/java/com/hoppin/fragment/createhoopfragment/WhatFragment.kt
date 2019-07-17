package com.hoppin.fragment.createhoopfragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hoppin.R
import com.hoppin.activity.ui.tabactivity.TabActivity
import com.hoppin.base.BaseFragment
import kotlinx.android.synthetic.main.activity_tab.*
import kotlinx.android.synthetic.main.fragment_what.*


class WhatFragment : BaseFragment(), View.OnClickListener {

    private var fragment: Fragment? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_what, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inItView()
    }

    fun inItView() {
        (context as TabActivity).iv_back.visibility = View.GONE
        btn_next.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        when (view.id) {

            R.id.btn_next -> {

                fragment = WhenFragment()
                displaySelectedFragment(fragment as WhenFragment)
            }

        }

    }

    fun displaySelectedFragment(fragment: Fragment) {
        val fragmentTransaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()
    }

}
