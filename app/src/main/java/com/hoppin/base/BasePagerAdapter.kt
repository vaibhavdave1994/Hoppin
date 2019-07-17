package com.hoppin.base

/**
 * Created by Ravi Birla on 26,June,2019
 */

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.hoppin.data.model.FragmentPagerModel

class BasePagerAdapter(fm: FragmentManager, context : Context?, fragments: ArrayList<FragmentPagerModel>)
    : FragmentPagerAdapter(fm) {
    var context : Context ?= null
    var fragment : ArrayList<FragmentPagerModel> ? = null
    init {
        this.context = context
        this.fragment = fragments
    }

    override fun getItem(position: Int): Fragment {
        return fragment!!.get(position).fragment
    }

    override fun getCount(): Int {
        return  fragment!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragment?.get(position)?.fragmentName
    }
}