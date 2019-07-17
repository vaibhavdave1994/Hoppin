package com.hoppin.fragment.createhoopfragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.hoppin.R
import com.hoppin.activity.ui.tabactivity.TabActivity
import com.hoppin.base.BaseFragment
import com.hoppin.fragment.ProfileFragment
import com.hoppin.helper.Utility
import kotlinx.android.synthetic.main.activity_tab.*
import kotlinx.android.synthetic.main.fragment_when.*

class WhenFragment : BaseFragment(), View.OnClickListener {

    lateinit var utility: Utility
    lateinit var date:String
    lateinit var fragment:Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_when, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        utility = Utility()
        inItView()
    }

    private fun inItView() {
        (context as TabActivity).iv_back.visibility = View.VISIBLE
        rl_startdate.setOnClickListener(this)
        (context as TabActivity).iv_back.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.rl_startdate -> {
            }
            R.id.iv_back -> {
            }
        }
    }

}
