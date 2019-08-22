package com.hoppin.fragment.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.hoppin.R


class MyHoopAdapter(var c: Context ,hoopDialogListner:HoopDialogListener) : RecyclerView.Adapter<MyHoopAdapter.MyViewHolder>() {
    var context: Context? = null
    var hoopDialogListner:HoopDialogListener? = null

    interface HoopDialogListener{
        fun dialog(position: Int)
    }


    init {
        this.context = c
        this.hoopDialogListner = hoopDialogListner

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.myhooplayout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    //TOTAL SPACECRAFTS
    override fun getItemCount(): Int {
        return 10
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var iv_menu:ImageView

        init {
            iv_menu = itemView.findViewById(R.id.iv_menu)
            iv_menu.setOnClickListener {
                hoopDialogListner?.dialog(adapterPosition)
            }
        }
    }
}
