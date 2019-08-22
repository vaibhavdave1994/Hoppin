package com.hoppin.activity.hoopActivity.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.hoppin.R
import com.hoppin.activity.hoopActivity.model.ContactModel

/**
 * Created by Ravi Birla on 09,July,2019
 */
class SelectGuestAdapter(var c: Context, value: ArrayList<Boolean>, contectList: List<ContactModel>) : RecyclerView.Adapter<SelectGuestAdapter.MyViewHolder>() {
    var context: Context? = null
    var selectedPos: Int
    var value: ArrayList<Boolean>
    var contectList: List<ContactModel>

    init {
        this.context = c
        selectedPos = 6
        this.value = value
        this.contectList = contectList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.selectguest_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (selectedPos == position) {
            holder.rlayout.visibility = View.GONE
            holder.tv_sms.visibility = View.VISIBLE
        } else {
            holder.rlayout.visibility = View.VISIBLE
            holder.tv_sms.visibility = View.GONE
        }

        if (position > 6) {
            val contactModel = contectList[position - 6]
            if (!contactModel.photoURI.toString().isEmpty()) {
                Glide.with(c).load(contactModel.photoURI.toString()).centerCrop().placeholder(R.drawable.default_image).into(holder.iv_hoopimage);
                holder.tv_invite.visibility = View.VISIBLE
                holder.iv_unselected.visibility = View.GONE

            }
            if (!contactModel.name.isEmpty()) {
                holder.tv_event_name.setText(contactModel.name)
            } else {
                holder.tv_event_name.setText(contactModel.mobileNumber)

            }
        } else {
            holder.tv_event_name.setText(String.format("Effortless Events"))
            holder.iv_hoopimage.setImageDrawable(c.resources.getDrawable(R.drawable.party_image5))
            holder.tv_invite.visibility = View.GONE
            holder.iv_unselected.visibility = View.VISIBLE

        }

        if (value.get(position)) {
            holder.rlayout.setBackgroundColor(c.resources.getColor(R.color.colorWhite))
            holder.check_guest.visibility = View.GONE
            if (position > 6) {
                holder.rlayout.setBackgroundColor(c.resources.getColor(R.color.colorWhite))
                holder.check_guest.visibility = View.GONE
                holder.iv_unselected.visibility = View.GONE
            }

        } else {

            holder.rlayout.setBackgroundColor(c.resources.getColor(R.color.colorGray4))
            holder.check_guest.visibility = View.VISIBLE
            holder.iv_unselected.visibility = View.VISIBLE

            if (position > 6) {
                holder.rlayout.setBackgroundColor(c.resources.getColor(R.color.colorWhite))
                holder.check_guest.visibility = View.GONE
                holder.iv_unselected.visibility = View.GONE
            }
        }

    }

    //TOTAL SPACECRAFTS
    override fun getItemCount(): Int {
        return contectList.size + 6
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {


        internal var rlayout: RelativeLayout
        internal var rl_privioushoop: LinearLayout
        internal var tv_sms: TextView
        internal var tv_invite: TextView
        internal var tv_event_name: TextView
        internal var iv_hoopimage: ImageView
        internal var iv_unselected: ImageView
        internal var check_guest: ImageView


        init {


            rlayout = itemView.findViewById(R.id.rlayout)
            rl_privioushoop = itemView.findViewById(R.id.rl_privioushoop)
            tv_sms = itemView.findViewById(R.id.tv_sms)
            tv_invite = itemView.findViewById(R.id.tv_invite)
            iv_hoopimage = itemView.findViewById(R.id.iv_hoopimage)
            check_guest = itemView.findViewById(R.id.check_guest)
            tv_event_name = itemView.findViewById(R.id.tv_event_name)
            iv_unselected = itemView.findViewById(R.id.iv_unselected)

            rl_privioushoop.setOnClickListener(this)
            tv_invite.setOnClickListener(this)
        }

        override fun onClick(p0: View) {

            when (p0.id) {
                R.id.rl_privioushoop -> {

                    if (value.get(adapterPosition)) {
                        value.set(adapterPosition, false)
                        notifyDataSetChanged()
                    } else {
                        value.set(adapterPosition, true)
                        notifyDataSetChanged()
                    }

                }

                R.id.tv_invite ->{

                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    val shareBodyText = "Check it out. Your message goes here"
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here")
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText)
                    context?.startActivity(Intent.createChooser(sharingIntent, "Shearing Option"))
                }

            }


        }
    }


}

