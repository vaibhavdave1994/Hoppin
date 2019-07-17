package com.hoppin.activity.ui.authantication.hoopinstart

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.hoppin.R
import com.hoppin.activity.ui.authantication.hoopinstart.model.IntroModel
import com.hoppin.activity.ui.authantication.login.LoginActivity
import com.hoppin.activity.ui.authantication.signup.SignUpActivity
import com.hoppin.activity.ui.tabactivity.TabActivity
import com.hoppin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_hoopin_started.*


class HoopinStartedActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_hoopin_started)

        initViews()
    }


    private fun initViews() {
        viewpager.adapter = MyPagerAdapter(this)
        dots.setupWithViewPager(viewpager, true)
        clicklistener()
    }

    private fun clicklistener() {
        btn_signin.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
        rl_facebook.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {

            R.id.btn_signin -> {
                btn_signin.background = ContextCompat.getDrawable(this, R.drawable.app_btn)
                btn_signup.background = ContextCompat.getDrawable(this, R.drawable.transperent_btn)
                btn_signin.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
                btn_signup.setTextColor(ContextCompat.getColor(this, R.color.colorGray1))
                intent = Intent(this@HoopinStartedActivity,LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_signup -> {

                btn_signin.background = ContextCompat.getDrawable(this, R.drawable.transperent_btn)
                btn_signup.background = ContextCompat.getDrawable(this, R.drawable.app_btn)
                btn_signin.setTextColor(ContextCompat.getColor(this, R.color.colorGray1))
                btn_signup.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
                intent = Intent(this@HoopinStartedActivity,SignUpActivity::class.java)
                startActivity(intent)
            }
            R.id.rl_facebook ->{

                intent = Intent(this@HoopinStartedActivity,TabActivity ::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    class MyPagerAdapter(mContext: Context) : PagerAdapter() {
        var mContext: Context

        init {
            this.mContext = mContext
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }


        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val modelObject = IntroModel.values()[position]
            val inflater = LayoutInflater.from(mContext)
            val layout = inflater.inflate(modelObject.getLayoutResId(), container, false) as ViewGroup
            container.addView(layout)
            return layout
        }

        override fun getCount(): Int {
            return IntroModel.values().size
        }

    }
}


