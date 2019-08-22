package com.hoppin.fragment.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hoppin.R
import com.hoppin.activity.hoopinActivity.DetailRequestActivity


class HoopRequestAdapter(var c: Context) : RecyclerView.Adapter<HoopRequestAdapter.MyViewHolder>() {
    var context: Context? = null

    init {
        this.context = c
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.hooprequestlayout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    //TOTAL SPACECRAFTS
    override fun getItemCount(): Int {
        return 10
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var intent: Intent? = null

        init {
            itemView.setOnClickListener {
                intent = Intent(c, DetailRequestActivity::class.java)
                c.startActivity(intent)
            }
        }
    }
}
