package com.hoppin.fragment.tabfragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hoppin.R
import com.hoppin.activity.ui.authantication.hoopinstart.HoopinStartedActivity
import com.hoppin.activity.ui.tabactivity.TabActivity
import com.hoppin.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment(), View.OnClickListener {

    lateinit var intent: Intent
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        inItView()
    }

    fun inItView() {

       /* (activity as TabActivity).updateStatusBarColor("#00A65B")
        tv_logout.setOnClickListener(this)*/

    }

    override fun onClick(p0: View) {

        when (p0.id) {

            R.id.tv_logout -> {
                intent = Intent(context, HoopinStartedActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
