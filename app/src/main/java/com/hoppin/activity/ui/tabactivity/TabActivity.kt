package com.hoppin.activity.ui.tabactivity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.hoppin.R
import com.hoppin.base.BaseActivity
import com.hoppin.fragment.tabfragment.*
import kotlinx.android.synthetic.main.activity_tab.*


class TabActivity : BaseActivity(), View.OnClickListener {

    private var fragment: Fragment? = null
    private var doubleBackToExitPressedOnce: Boolean = false
    private val runnable: Runnable? = null
    private var lastClick = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        setContentView(R.layout.activity_tab)
        inItView()
    }

    fun inItView() {

        if (lastClick != R.id.img_one) {
            lastClick = R.id.img_one
            rlheader.visibility = View.VISIBLE
            view.visibility = View.GONE
            tv_headername.setText(String.format("Hoopin"))
            fragment = HoopinFragment()
            displaySelectedFragment(fragment as HoopinFragment)
            img_one.setImageResource(R.drawable.ic_active_event_ico)
            img_two.setImageResource(R.drawable.ic_inactive_group_ico)
            img_three.setImageResource(R.drawable.inactive_add_ico)
            img_four.setImageResource(R.drawable.ic_inactive_notification_ico)
            img_five.setImageResource(R.drawable.ic_inactive_profile_ico)
            iv_back.visibility = View.GONE
        }
        img_one.setOnClickListener(this)
        img_two.setOnClickListener(this)
        rl_imagethree.setOnClickListener(this)
        img_four.setOnClickListener(this)
        img_five.setOnClickListener(this)


    }

    override fun onClick(p0: View) {

        when (p0.id) {



            R.id.img_one -> {
                if (lastClick != R.id.img_one) {
                    lastClick = R.id.img_one
                    rlheader.visibility = View.VISIBLE
                    view.visibility = View.GONE
                    fragment = HoopinFragment()
                    tv_headername.setText(String.format("Hoopin"))
                    displaySelectedFragment(fragment as HoopinFragment)
                    updateTabView(R.id.img_one)
                    iv_back.visibility = View.GONE
                    view.visibility = View.GONE


                }
            }

            R.id.img_two -> {

                if (lastClick != R.id.img_two) {
                    lastClick = R.id.img_two
                    view.visibility = View.VISIBLE
                    fragment = HoopinCircleFragment()
                    tv_headername.setText(String.format("Hoopin Circle"))
                    displaySelectedFragment(fragment as HoopinCircleFragment)
                    updateTabView(R.id.img_two)
                    iv_back.visibility = View.GONE
                    rlheader.visibility = View.VISIBLE


                }
            }

            R.id.rl_imagethree -> {

                if (lastClick != R.id.rl_imagethree) {
                    lastClick = R.id.rl_imagethree
                    rlheader.visibility = View.VISIBLE
                    view.visibility = View.VISIBLE
                    tv_headername.setText(String.format("Create Hoopin"))
                    fragment = CreateHoopFragment()
                    displaySelectedFragment(fragment as CreateHoopFragment)
                    updateTabView(R.id.rl_imagethree)
                    iv_back.visibility = View.GONE

                }
            }
            R.id.img_four -> {

                if (lastClick != R.id.img_four) {
                    lastClick = R.id.img_four
                    rlheader.visibility = View.VISIBLE
                    view.visibility = View.VISIBLE
                    tv_headername.setText(String.format("Notification"))
                    fragment = NotificationFragment()
                    displaySelectedFragment(fragment as NotificationFragment)
                    updateTabView(R.id.img_four)
                    iv_back.visibility = View.GONE

                }
            }
            R.id.img_five -> {

                if (lastClick != R.id.img_five) {
                    lastClick = R.id.img_five
                    rlheader.visibility = View.GONE
                    fragment = ProfileFragment()
                    displaySelectedFragment(fragment as ProfileFragment)
                    updateTabView(R.id.img_five)
                    iv_back.visibility = View.GONE


                }
            }
        }
    }

    private fun updateTabView(flag: Int) {
        when (flag) {
            R.id.img_one -> {
                img_one.setImageResource(R.drawable.ic_active_event_ico)
                img_two.setImageResource(R.drawable.ic_inactive_group_ico)
                img_three.setImageResource(R.drawable.inactive_add_ico)
                img_four.setImageResource(R.drawable.ic_inactive_notification_ico)
                img_five.setImageResource(R.drawable.ic_inactive_profile_ico)

            }

            R.id.img_two -> {
                img_one.setImageResource(R.drawable.ic_inactive_event_ico)
                img_two.setImageResource(R.drawable.ic_active_group_ico)
                img_three.setImageResource(R.drawable.inactive_add_ico)
                img_four.setImageResource(R.drawable.ic_inactive_notification_ico)
                img_five.setImageResource(R.drawable.ic_inactive_profile_ico)

            }
            R.id.rl_imagethree -> {
                img_one.setImageResource(R.drawable.ic_inactive_event_ico)
                img_two.setImageResource(R.drawable.ic_inactive_group_ico)
                img_three.setImageResource(R.drawable.active_add_ico)
                img_four.setImageResource(R.drawable.ic_inactive_notification_ico)
                img_five.setImageResource(R.drawable.ic_inactive_profile_ico)

            }

            R.id.img_four -> {
                img_one.setImageResource(R.drawable.ic_inactive_event_ico)
                img_two.setImageResource(R.drawable.ic_inactive_group_ico)
                img_three.setImageResource(R.drawable.inactive_add_ico)
                img_four.setImageResource(R.drawable.ic_active_notification_ico)
                img_five.setImageResource(R.drawable.ic_inactive_profile_ico)

            }

            R.id.img_five -> {
                img_one.setImageResource(R.drawable.ic_inactive_event_ico)
                img_two.setImageResource(R.drawable.ic_inactive_group_ico)
                img_three.setImageResource(R.drawable.inactive_add_ico)
                img_four.setImageResource(R.drawable.ic_inactive_notification_ico)
                img_five.setImageResource(R.drawable.ic_active_profile_ico)


            }
        }
    }

    fun displaySelectedFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, fragment)
        fragmentTransaction.commit()
    }


    override fun onBackPressed() {
        /* Handle double click to finish activity*/
        val handler = Handler()
        val fm = supportFragmentManager
        val i = fm.backStackEntryCount
        if (i > 0) {
            fm.popBackStack()
        } else if (!doubleBackToExitPressedOnce) {
            doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show()
            handler.postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else {
            handler.removeCallbacks(runnable)
            finishAffinity()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
        fragment!!.onActivityResult(requestCode, resultCode, data)
    }

    fun updateStatusBarColor(color: String) {// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor(color)
        }
    }

}
