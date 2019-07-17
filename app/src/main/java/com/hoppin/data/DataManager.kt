package com.hoppin.data

import com.hoppin.data.local.PreferencesHelper
import com.hoppin.data.remote.ApiHelper


interface DataManager : PreferencesHelper, ApiHelper {
}