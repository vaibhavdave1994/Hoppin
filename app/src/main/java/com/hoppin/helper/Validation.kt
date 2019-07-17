package com.hoppin.helper

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import com.hoppin.R

/**
 * Created by Ravi Birla on 08,July,2019
 */
class Validation(private val context: Context) {

    private fun getString(editText: EditText): String {
        return editText.text.toString()
    }


    //New Code
    fun isFirstNameValid(editText: EditText): Boolean {
        if (getString(editText).isEmpty()) {
            Toast.makeText(context, context.getString(R.string.firstNameEmptyError), Toast.LENGTH_SHORT).show()
            return false
        } else if (editText.length() < 3) {
            Toast.makeText(context, context.getString(R.string.firstNameLengthError), Toast.LENGTH_SHORT).show()

            return false
        } else {
            return true
        }
    }

    fun isLastNameValid(editText: EditText): Boolean {
        if (getString(editText).isEmpty()) {
            Toast.makeText(context, context.getString(R.string.lastNameEmptyError), Toast.LENGTH_SHORT).show()
            return false
        } else if (editText.length() < 3) {
            Toast.makeText(context, context.getString(R.string.lastNameLengthError), Toast.LENGTH_SHORT).show()

            return false
        } else {
            return true
        }
    }

    fun isEmailValid(editText: EditText): Boolean {
        if (getString(editText).isEmpty()) {
            Toast.makeText(context, context.getString(R.string.emailEmptyError), Toast.LENGTH_SHORT).show()
            return false
        } else {
            val bool = android.util.Patterns.EMAIL_ADDRESS.matcher(getString(editText)).matches()
            if (!bool) {
                Toast.makeText(context, context.getString(R.string.emailInvalidError), Toast.LENGTH_SHORT).show()
            }
            return bool
        }
    }

    fun isPasswordValid(editText: EditText): Boolean {
        if (getString(editText).isEmpty()) {
            editText.requestFocus()
            Toast.makeText(context, context.getString(R.string.passEmptyError), Toast.LENGTH_SHORT).show()

            return false
        }/* else if (editText.text.length >= 6) {
            editText.requestFocus()
            return true
        }*/ else {
            editText.requestFocus()
            return true
        }
    }

    fun isPhoneNumberValid(editText: EditText): Boolean {
        if (getString(editText).isEmpty()) {
            Toast.makeText(context, context.getString(R.string.lastNameEmptyError), Toast.LENGTH_SHORT).show()
            return false
        }  else {
            return true
        }
    }


}