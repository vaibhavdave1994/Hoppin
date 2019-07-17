package com.hoppin.helper

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.net.ConnectivityManager
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ravi Birla on 29,June,2019
 */
class Utility {

    fun startDateMathod(context: Context, listener: OnDateSelectedListener) {
        val calendar = Calendar.getInstance()
        val year1 = calendar.get(Calendar.YEAR)
        val month1 = calendar.get(Calendar.MONTH)
        val day1 = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    val date = Date()
                    val strDateFormat = "hh:mm a"
                    @SuppressLint("SimpleDateFormat") val dateFormat = SimpleDateFormat(strDateFormat)
                    val formattedDate = dateFormat.format(date)
                    val monthString = DateFormatSymbols().months[month]
                    val disDate = day.toString() + " " + monthString + " " + year.toString() + " , " + formattedDate
                    listener.onDateSelect(disDate)
                }, year1, month1, day1)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    interface OnDateSelectedListener {
        fun onDateSelect(formatedDate: String)
    }

    fun optionDateMathod(context: Context, listener: OnOptionDateSelectedListener) {
        val calendar = Calendar.getInstance()
        val year1 = calendar.get(Calendar.YEAR)
        val month1 = calendar.get(Calendar.MONTH)
        val day1 = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(context,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    val date = Date()
                    val strDateFormat = "hh:mm a"
                    @SuppressLint("SimpleDateFormat") val dateFormat = SimpleDateFormat(strDateFormat)
                    val formattedDate = dateFormat.format(date)
                    val monthString = DateFormatSymbols().months[month]
                    val disDate = day.toString() + " " + monthString + " " + year.toString() + " , " + formattedDate
                    listener.onOptionDateSelect(disDate)
                }, year1, month1, day1)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    interface OnOptionDateSelectedListener {
        fun onOptionDateSelect(formatedDate: String)
    }

    fun checkInternetConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}