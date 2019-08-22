package com.hoppin.activity.hoopActivity.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.hoppin.R


class HoopMemoryAdapter(var c: Context) : RecyclerView.Adapter<HoopMemoryAdapter.MyViewHolder>() {
    var context: Context? = null

    init {
        this.context = c

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.hoopmemory_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position==0){
            holder.rl_addmemory.visibility = View.VISIBLE
        }

    }

    //TOTAL SPACECRAFTS
    override fun getItemCount(): Int {
        return 10
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        internal var  rl_addmemory:RelativeLayout

        init {
            rl_addmemory = itemView.findViewById(R.id.rl_addmemory)
        }

    }


}

