package com.hoppin.activity.ui.authantication.forgetpassword

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.hoppin.R
import com.hoppin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setContentView(R.layout.activity_forget_password)
        inItView()
    }

    @SuppressLint("NewApi")
    fun inItView() {
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorWhite));
        iv_back.setOnClickListener(this)

    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.iv_back -> {
                onBackPressed()
            }
        }
    }

}
