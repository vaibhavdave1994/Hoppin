package com.hoppin.helper

import android.content.Context
import android.support.design.widget.TextInputLayout
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.hoppin.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

/**
 * Created by Ravi Birla on 08,July,2019
 */
class Validation(private val context: Context) {

    var EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    var PASS_STRING = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"


    fun checkEmail(editText: EditText, inputEditText: TextInputLayout): Boolean {
        if (editText.text.toString().isEmpty()) {
            inputEditText.isErrorEnabled = true

            inputEditText.setError(context.getString(R.string.emailEmptyError))
            return false

        } else if (!Pattern.compile(EMAIL_STRING).matcher(editText.text.toString()).matches()) {
            inputEditText.isErrorEnabled = true

            inputEditText.setError(context.getString(R.string.emailInvalidError))
            return false
        } else {
            inputEditText.isErrorEnabled = false

            inputEditText.setError("")
            return true
        }
    }

    fun checkPassword(editText: EditText, inputEditText: TextInputLayout): Boolean {
        if (editText.text.toString().isEmpty()) {
            inputEditText.isErrorEnabled = true
            inputEditText.setError(context.getString(R.string.passEmptyError))
            return false
        } else {
            inputEditText.isErrorEnabled = false
            inputEditText.setError("")
            return true
        }
    }
 fun checkPassword1(editText: EditText, inputEditText: TextInputLayout): Boolean {
        if (editText.text.toString().isEmpty()) {
            inputEditText.isErrorEnabled = true
            inputEditText.setError(context.getString(R.string.passEmptyError))
            return false
        }else if (!Pattern.compile(PASS_STRING).matcher(editText.text).matches()) {
            inputEditText.isErrorEnabled = true
            inputEditText.setError(context.getString(R.string.passlenthError))
            return false
        }
        else {
            inputEditText.isErrorEnabled = false
            inputEditText.setError("")
            return true
        }
    }

    fun checkMobile(editText: EditText, view: View, text: TextView): Boolean {
        if (editText.text.toString().isEmpty()) {

            view.setBackgroundColor(ContextCompat.getColor(context,R.color.colorRedError))
            text.visibility = View.VISIBLE
            return false
        } else {
            view.setBackgroundColor(ContextCompat.getColor(context,R.color.colorLight))
            text.visibility = View.GONE
            return true
        }

    }

    fun checkFirstName(editText: EditText, inputEditText: TextInputLayout): Boolean {
        if (editText.text.toString().isEmpty()) {
            inputEditText.isErrorEnabled = true
            inputEditText.setError(context.getString(R.string.firstNameEmptyError))
            return false
        } else {
            inputEditText.isErrorEnabled = false
            inputEditText.setError("")
            return true
        }

    }

    fun checkLastName(editText: EditText, inputEditText: TextInputLayout): Boolean {
        if (editText.text.toString().isEmpty()) {

            inputEditText.isErrorEnabled = true
            inputEditText.setError(context.getString(R.string.lastNameEmptyError))
            return false
        } else {
            inputEditText.isErrorEnabled = false
            inputEditText.setError("")
            return true
        }

    }

    fun checkTitle(editText: EditText,text: TextView): Boolean {
        if (editText.text.toString().isEmpty()) {
            text.visibility = View.VISIBLE
            return false
        } else {
            text.visibility = View.GONE
            return true
        }

    }



    fun checkLoction(editText: EditText,text: TextView): Boolean {
        if (editText.text.toString().isEmpty()) {
            text.visibility = View.VISIBLE
            return false
        } else {
            text.visibility = View.GONE
            return true
        }

    }

    fun checkStartDate(editText: TextView,text: TextView): Boolean {
        if (editText.text.toString().isEmpty()) {
            text.visibility = View.VISIBLE
            return false
        } else {
            text.visibility = View.GONE
            return true
        }

    }


    fun checkCode(editText: TextView,text: TextView): Boolean {
        if (editText.text.toString().isEmpty() ) {
            text.visibility = View.VISIBLE
            return false
        }
        else if (editText.text.toString().length == 1  ) {
            text.visibility = View.VISIBLE
            return false
        }
        else if (editText.text.toString().length == 2  ) {
            text.visibility = View.VISIBLE
            return false
        }
        else if (editText.text.toString().length == 3  ) {
            text.visibility = View.VISIBLE
            return false
        }
        else if (editText.text.toString().length == 4  ) {
            text.visibility = View.VISIBLE
            return false
        }
        else if (editText.text.toString().length == 5  ) {
            text.visibility = View.VISIBLE
            return false
        }
        else {
            text.visibility = View.GONE
            return true
        }

    }
}