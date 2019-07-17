package com.hoppin

/**
 * Created by Ravi Birla on 27,June,2019
 */
import android.app.Application
import com.hoppin.data.AppDataManager

class Hoopin : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        dataManager1 = AppDataManager.getAppDataManager(this)
    }

    companion object {
        private lateinit var dataManager1 : AppDataManager
        var instance: Hoopin? = null


        fun getDataManager() :AppDataManager{
            return dataManager1
        }
    }

}