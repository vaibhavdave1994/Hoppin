package com.hoppin.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.hoppin.R
import com.hoppin.activity.ui.authantication.hoopinstart.HoopinStartedActivity
import com.hoppin.base.BaseActivity

class SplashScreenActivity : BaseActivity() {

    var mRunnable: Runnable? = null
    var mHandler = Handler()
    val DELAY: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)
        inItView()
    }

    fun inItView() {

        mRunnable = Runnable {
            intent = Intent(this@SplashScreenActivity, HoopinStartedActivity::class.java)
            startActivity(intent)
            finish()
        }
        mHandler.postDelayed(mRunnable, DELAY)
    }


}
