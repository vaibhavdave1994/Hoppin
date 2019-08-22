package com.hoppin.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.hoppin.fragment.hoopinfragment.HoopConfirmFragment
import com.hoppin.fragment.hoopinfragment.HoopRequestFragment
import com.hoppin.fragment.hoopinfragment.MyHoopFragment

/**
 * Created by Ravi Birla on 17,July,2019
 */
class HoopViewPager(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        if (position == 0) {
            fragment = HoopRequestFragment()
        } else if (position == 1) {
            fragment = MyHoopFragment()
        } else if (position == 2) {
            fragment = MyHoopFragment();
        }
        return fragment
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "Hoop Request"
        } else if (position == 1) {
            title = "Confirmed"
        } else if (position == 2) {
            title = "My Hoop"
        }
        return title
    }
}

