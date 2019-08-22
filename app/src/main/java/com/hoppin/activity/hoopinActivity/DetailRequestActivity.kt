package com.hoppin.activity.hoopinActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.text.HtmlCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import com.bcgdv.asia.lib.ticktock.TickTockView
import com.hoppin.R
import com.hoppin.activity.hoopActivity.adapter.DetailGuestAdapter
import com.hoppin.base.BaseActivity
import hiennguyen.me.circleseekbar.CircleSeekBar
import kotlinx.android.synthetic.main.activity_detail_request.*
import kotlinx.android.synthetic.main.detail_layout.*
import java.text.SimpleDateFormat
import java.util.*


class DetailRequestActivity : BaseActivity(), View.OnClickListener, CircleSeekBar.OnSeekBarChangedListener {


    lateinit var detailGuestAdapter: DetailGuestAdapter
    lateinit var description: String
    lateinit var see: String
    private val EVENT_DATE_TIME = "2019-07-24 07:59:00"
    private val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private val linear_layout_1: LinearLayout? = null
    val linear_layout_2: LinearLayout? = null
    private val handler = Handler()
    private var runnable: Runnable? = null
    private var diff: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_detail_request)
        countDownStart()
        inItView()
    }

    fun inItView() {
        iv_back.setOnClickListener(this)
        rl_share.setOnClickListener(this)
//        circleSeekBar.setSeekBarChangeListener(this)
        //circleSeekBar.setProgressDisplayAndInvalidate(13);

        adapterData()
        description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard.."
        see = "<font color=\"#00A65B\"><U> <small>" + "See More" + " </small></U></font>"

        tv_description.text = HtmlCompat.fromHtml(description + "" + see, 0)

        /*if (mCountDown != null) {
            mCountDown.setOnTickListener(TickTockView.OnTickListener { timeRemaining ->
                val seconds = (timeRemaining / 1000).toInt() % 60
                val minutes = (timeRemaining / (1000 * 60) % 60).toInt()
                val hours = (timeRemaining / (1000 * 60 * 60) % 24).toInt()
                val days = (timeRemaining / (1000 * 60 * 60 * 24)).toInt()
                val hasDays = days > 0
                String.format("%1$02d%4\$s %2$02d%5\$s %3$02d%6\$s",
                        if (hasDays) days else hours,
                        if (hasDays) hours else minutes,
                        if (hasDays) minutes else seconds,
                        if (hasDays) "d" else "h",
                        if (hasDays) "h" else "m",
                        if (hasDays) "m" else "s")
            })
        }*/

        if (mCountDown != null) {
            mCountDown.setOnTickListener { timeRemaining ->
                val seconds = (timeRemaining / 1000).toInt() % 60
                val minutes = (timeRemaining / (1000 * 60) % 60).toInt()
                val hours = (timeRemaining / (1000 * 60 * 60) % 24).toInt()
                val days = (timeRemaining / (1000 * 60 * 60 * 24)).toInt()
                val hasDays = days > 0
                String.format("%1$02d%4\$s %2$02d%5\$s %3$02d%6\$s",
                        if (hasDays) days else hours,
                        if (hasDays) hours else minutes,
                        if (hasDays) minutes else seconds,
                        if (hasDays) "d" else "h",
                        if (hasDays) "h" else "m",
                        if (hasDays) "m" else "s")
            }
        }


    }

    fun adapterData() {
        detailGuestAdapter = DetailGuestAdapter(this)
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.layoutManager = mLayoutManager
        //addprop_recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.adapter = detailGuestAdapter
    }


    override fun onClick(p0: View) {

        when (p0.id) {
            R.id.iv_back -> {
                onBackPressed()
            }


        }
    }

    override fun onStartTrackingTouch(circleSeekBar: CircleSeekBar?) {


    }

    override fun onPointsChanged(circleSeekBar: CircleSeekBar?, points: Int, fromUser: Boolean) {


    }

    override fun onStopTrackingTouch(circleSeekBar: CircleSeekBar?) {
    }

    private fun countDownStart() {

        runnable = object : Runnable {
            @SuppressLint("SimpleDateFormat")
            override fun run() {
                try {
                    handler.postDelayed(this, 1000)
                    val dateFormat = SimpleDateFormat(DATE_FORMAT)
                    val event_date = dateFormat.parse(EVENT_DATE_TIME)
                    val current_date = Date()
                    if (!current_date.after(event_date)) {
                        diff = event_date.time - current_date.time

                        val Days = diff / (24 * 60 * 60 * 1000)
                        val Hours = diff / (24 * 60 * 1000) % 24
                        val Minutes = diff / (60 * 1000) % 60
                        val Second = diff / 1000 % 60



                        tv_day.text = String.format("%02d", Days)
                        tv_hour.text = String.format("%02d", Hours)
                        tv_min.text = String.format("%02d", Minutes)

                        val end = Calendar.getInstance()
                        end.add(Calendar.HOUR, Hours.toInt())
                        end.add(Calendar.MINUTE, Minutes.toInt())
                        end.add(Calendar.SECOND, 5)

                        val start = Calendar.getInstance()
                        start.add(Calendar.MINUTE, -1)
                        if (mCountDown != null) {
                            mCountDown.start(start, end)
                        }

                      /*  val end = Calendar.getInstance()
                        end.add(Calendar.DAY_OF_MONTH, Days.toInt())
                        end.add(Calendar.HOUR, Hours.toInt())
                        end.add(Calendar.MINUTE, Minutes.toInt())
                        val start = Calendar.getInstance()
                        start.add(Calendar.MINUTE, 1)
                        if (mCountDown != null) {
                            mCountDown.start(start, end)
                        }*/





                    } else {
                        linear_layout_1!!.visibility = View.VISIBLE
                        linear_layout_2!!.visibility = View.GONE
                        handler.removeCallbacks(runnable)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        }
        handler.postDelayed(runnable, 0)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }

    /* override fun onStart() {
         super.onStart()
         val end = Calendar.getInstance()
         end.add(Calendar.DAY_OF_MONTH,2)
         end.add(Calendar.HOUR, 4)
         end.add(Calendar.MINUTE, 4)

         val start = Calendar.getInstance()
         start.add(Calendar.MINUTE, 1)
         if (mCountDown != null) {
             mCountDown.start(start, end)
         }



     }*/

}
