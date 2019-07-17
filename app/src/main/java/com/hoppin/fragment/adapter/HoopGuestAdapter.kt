package com.hoppin.fragment.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hoppin.R

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

    }

    //TOTAL SPACECRAFTS
    override fun getItemCount(): Int {
        return 10
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}
