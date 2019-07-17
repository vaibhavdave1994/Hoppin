package com.hoppin.activity.hoopActivity.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RelativeLayout
import com.hoppin.R
import com.hoppin.R.id.radiobutton_privioushoop
import com.hoppin.activity.hoopActivity.model.PriviousHoopDataModel
import kotlinx.android.synthetic.main.privioushoop_layout.view.*

/**
 * Created by Ravi Birla on 09,July,2019
 */
class PriviousHoopAdapter(var c: Context) : RecyclerView.Adapter<PriviousHoopAdapter.MyViewHolder>() {
    var context: Context? = null
    var selectedPos:Int

    init {
        this.context = c
        selectedPos = 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.privioushoop_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (selectedPos == position)
        {
            holder.radiobutton_privioushoop.setChecked(true)
            selectedPos = position
        }
        else{
            holder.radiobutton_privioushoop.setChecked(false)

        }
    }

    //TOTAL SPACECRAFTS
    override fun getItemCount(): Int {
        return 10
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {


        internal var rl_privioushoop: RelativeLayout
        lateinit var intent: Intent
        internal var radiobutton_privioushoop: RadioButton

        init {


            rl_privioushoop = itemView.findViewById(R.id.rl_privioushoop)
            radiobutton_privioushoop = itemView.findViewById(R.id.radiobutton_privioushoop)

            rl_privioushoop.setOnClickListener(this)
        }

        override fun onClick(p0: View) {

            when(p0.id)
            {
                R.id.rl_privioushoop ->{

                    if(adapterPosition != -1)
                    {
                       if(selectedPos != adapterPosition)
                       {
                           selectedPos = adapterPosition
                           notifyDataSetChanged()
                       }
                    }
                }

            }
        }


    }
}
