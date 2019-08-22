package com.hoppin.fragment.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.hoppin.R
import com.hoppin.activity.hoopActivity.GuestinvitedActivity

/**
 * Created by Ravi Birla on 09,July,2019
 */
class HoopGuestAdapter(var c: Context) : RecyclerView.Adapter<HoopGuestAdapter.MyViewHolder>() {
    var context: Context? = null

    init {
        this.context = c
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.invitedguest_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (position == 1) {
            holder.rl_ivcount.visibility = View.VISIBLE
        }

    }

    //TOTAL SPACECRAFTS
    override fun getItemCount(): Int {
        return 2
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var rl_ivcount: RelativeLayout
        internal var intent: Intent? = null

        init {
            rl_ivcount = itemView.findViewById(R.id.rl_ivcount)
            rl_ivcount.setOnClickListener {
                intent = Intent(context, GuestinvitedActivity::class.java)
                context?.startActivity(intent)
            }
        }
    }

}
