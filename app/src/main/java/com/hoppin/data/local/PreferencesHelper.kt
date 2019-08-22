package com.hoppin.data.local

import android.app.Activity
import com.hoppin.data.model.LoginInfo
import com.hoppin.data.model.UserInfoBean
import java.util.*

interface PreferencesHelper {
    fun setInfo(info: String)
    fun isLoggedIn(): Boolean?
    fun getUserInfo(): UserInfoBean
    fun setUserInfo(userInfo: UserInfoBean)
    fun setLoginInfo(userInfo: LoginInfo)
    fun getLoginInfo(): LoginInfo
    fun setRememberMe(email: String, pwd: String)
    fun getRememberMe(): Array<String?>
    fun getHeader(): HashMap<String, String>
    fun logout(activity: Activity)
}