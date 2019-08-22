package com.hoppin.data

import android.app.Activity
import android.content.Context
import com.hoppin.data.local.AppPreferencesHelper
import com.hoppin.data.model.LoginInfo
import com.hoppin.data.model.UserInfoBean
import com.hoppin.data.remote.AppApiHelper


class AppDataManager(context: Context) : DataManager {


    private var instanceApi: AppApiHelper? = null
    private var instancePref: AppPreferencesHelper? = null
//     var mGson: Gson? = null

    init {
        instanceApi = AppApiHelper.getAppApiHelper()
        instancePref = AppPreferencesHelper.getAppPreferencesHelper(context)
//        mGson = GsonBuilder().create()
    }

    companion object {
        private var instanceApp: AppDataManager? = null;

        fun getAppDataManager(context: Context): AppDataManager {
            instanceApp = AppDataManager(context)
            return instanceApp as AppDataManager
        }
    }


    /* override fun doSignup(param: HashMap<String, String>): ANRequest<out ANRequest<*>>? {
         return instanceApi?.doSignup(param)
     }




     override fun checkEmailAvailability(param: HashMap<String, String>): ANRequest<out ANRequest<*>>? {
         return  instanceApi?.checkEmailAvailability(param)
     }*/

    override fun isLoggedIn(): Boolean? {
        return instancePref?.isLoggedIn()
    }


    override fun setRememberMe(email: String, pwd: String) {
        return instancePref!!.setRememberMe(email, pwd)
    }

    override fun getRememberMe(): Array<String?> {
        return instancePref!!.getRememberMe()
    }

    override fun getHeader(): java.util.HashMap<String, String> {
        return instancePref!!.getHeader()
    }

    override fun logout(activity: Activity) {
        return instancePref!!.logout(activity)
    }

    override fun setInfo(info: String) {
        instancePref?.setInfo(info)
    }

    override fun getUserInfo(): UserInfoBean {
        return instancePref!!.getUserInfo()
    }

    override fun setUserInfo(userInfo: UserInfoBean) {
        instancePref?.setUserInfo(userInfo)
    }

    override fun setLoginInfo(userInfo: LoginInfo) {
        instancePref?.setLoginInfo(userInfo)

    }

    override fun getLoginInfo(): LoginInfo {
        return instancePref!!.getLoginInfo()
    }

    /* override fun singleJobCreate(param: HashMap<String, String>): ANRequest<out ANRequest<*>>? {
         return instanceApi?.singleJobCreate(param)
     }

     override fun skills(): ANRequest<out ANRequest<*>>? {
     return instanceApi?.skills()
     }*/

}