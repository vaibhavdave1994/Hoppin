package com.hoppin.helper

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.app.AlertDialog

/**
 * Created by Ravi Birla on 13,August,2019
 */
class Alert {

    fun whyProvideAlert(context: Context) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setMessage("Only people who have your phone number can invite you to hoops. Providing it makes it easy for them to invite you.")
        alertDialog.setPositiveButton("OK") { dialog, which -> dialog.dismiss() }
        alertDialog.show()
    }

}