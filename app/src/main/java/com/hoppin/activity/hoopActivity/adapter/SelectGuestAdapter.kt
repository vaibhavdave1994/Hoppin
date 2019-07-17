package com.hoppin.activity.hoopActivity.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.hoppin.R

/**
 * Created by Ravi Birla on 09,July,2019
 */
class SelectGuestAdapter(var c: Context, value: ArrayList<Boolean>) : RecyclerView.Adapter<SelectGuestAdapter.MyViewHolder>() {
    var context: Context? = null
    var selectedPos: Int
    var value : ArrayList<Boolean>

    init {
        this.context = c
        selectedPos = 6
        this.value = value
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.selectguest_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (selectedPos == position) {
            holder.rlayout.visibility = View.GONE
            holder.tv_sms.visibility = View.VISIBLE
        }
        else{
            holder.rlayout.visibility = View.VISIBLE
            holder.tv_sms.visibility = View.GONE
        }

        if (position > 6) {
            holder.iv_hoopimage.setImageDrawable(c.resources.getDrawable(R.drawable.party_image3))
        }
        else{
            holder.iv_hoopimage.setImageDrawable(c.resources.getDrawable(R.drawable.party_image5))

        }

        if (value.get(position)) {
            holder.rlayout.setBackgroundColor(c.resources.getColor(R.color.colorWhite))
            holder.check_guest.visibility = View.GONE

        } else {

            holder.rlayout.setBackgroundColor(c.resources.getColor(R.color.colorGray4))
            holder.check_guest.visibility = View.VISIBLE

        }

    }

    //TOTAL SPACECRAFTS
    override fun getItemCount(): Int {
        return 10
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        internal var rlayout: RelativeLayout
        internal var rl_privioushoop: LinearLayout
        internal var tv_sms: TextView
        internal var iv_hoopimage: ImageView
        internal var check_guest: ImageView


        init {


            rlayout = itemView.findViewById(R.id.rlayout)
            rl_privioushoop = itemView.findViewById(R.id.rl_privioushoop)
            tv_sms = itemView.findViewById(R.id.tv_sms)
            iv_hoopimage = itemView.findViewById(R.id.iv_hoopimage)
            check_guest = itemView.findViewById(R.id.check_guest)

           rl_privioushoop.setOnClickListener(this)
        }

        override fun onClick(p0: View) {

            when (p0.id) {
                R.id.rl_privioushoop -> {

                    if (value.get(adapterPosition)) {
                        value.set(adapterPosition , false)
                        notifyDataSetChanged()
                    }else {
                        value.set(adapterPosition , true)
                        notifyDataSetChanged()
                    }
                }
            }

        }
    }


}

