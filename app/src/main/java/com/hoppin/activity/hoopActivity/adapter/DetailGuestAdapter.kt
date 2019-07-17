package com.hoppin.activity.hoopActivity.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.hoppin.R

/**
 * Created by Ravi Birla on 09,July,2019
 */
class DetailGuestAdapter(var c: Context) : RecyclerView.Adapter<DetailGuestAdapter.MyViewHolder>() {
    var context: Context? = null

    init {
        this.context = c
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.guestlayout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (position == 3)
        {
            holder.rl_ivcount.visibility=View.VISIBLE
        }

    }

    //TOTAL SPACECRAFTS
    override fun getItemCount(): Int {
        return 4
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var rl_ivcount:RelativeLayout

        init {
            rl_ivcount = itemView.findViewById(R.id.rl_ivcount)
        }
    }
}
