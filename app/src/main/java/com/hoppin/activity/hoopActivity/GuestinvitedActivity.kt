package com.hoppin.activity.hoopActivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.hoppin.R
import com.hoppin.activity.hoopActivity.adapter.InvitedGuestAdapter
import com.hoppin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_selcet_guest.*

class GuestinvitedActivity : BaseActivity(), View.OnClickListener {

    private var invitedGuestAdapter: InvitedGuestAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guestinvited)
        inItView()
        adapterData()
    }

    @SuppressLint("NewApi")
    fun inItView() {

        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorWhite));
        iv_back.setOnClickListener(this)
        invitedGuestAdapter = InvitedGuestAdapter(this)

    }

    fun adapterData() {
        invitedGuestAdapter = InvitedGuestAdapter(this)
        val mLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = mLayoutManager
        recycler_view.adapter = invitedGuestAdapter
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }
}
