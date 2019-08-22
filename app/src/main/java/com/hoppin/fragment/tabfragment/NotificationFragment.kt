package com.hoppin.fragment.tabfragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hoppin.R
import com.hoppin.activity.ui.tabactivity.TabActivity
import com.hoppin.base.BaseFragment

class NotificationFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as TabActivity).updateStatusBarColor("#ffffff")

    }

}
