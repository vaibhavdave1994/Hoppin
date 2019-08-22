package com.hoppin.activity.ui.authantication.signup.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ravi Birla on 14,August,2019
 */
@Parcelize
data class SignupModel(var firstName : String,
                       var lastName : String,
                       var email : String,
                       var phoneNumber : String,
                       var password : String,
                       var stauts : String,
                       var photourl : String) : Parcelable