package com.hoppin

/**
 * Created by Ravi Birla on 27,June,2019
 */
import android.app.Application
import android.util.Log
import com.hoppin.activity.ui.authantication.otpmessage.AppSignatureHelper
import com.hoppin.data.AppDataManager

class Hoopin : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        dataManager1 = AppDataManager.getAppDataManager(this)
        Log.e("testcode",AppSignatureHelper(this).appSignatures.toString())
    }

    companion object {
        private lateinit var dataManager1 : AppDataManager
        var instance: Hoopin? = null


        fun getDataManager() :AppDataManager{
            return dataManager1
        }
    }

}