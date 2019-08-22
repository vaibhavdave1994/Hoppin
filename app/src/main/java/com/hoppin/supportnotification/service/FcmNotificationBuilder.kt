package com.hoppin.supportnotification.service

import android.util.Log
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*

/**
 * Created by Ravi Birla on 05,February,2019
 */
class FcmNotificationBuilder private constructor() {

    private var mTitle: String? = null
    private var mMessage: String? = null
    private var mUsername: String? = null
    private var mUid: String? = null
    private var mFirebaseToken: String? = null
    private var mReceiverFirebaseToken: String? = null
    private var mReceiverFirebaseTokenGroup: JSONArray? = null
    private var payLoadEvent: String? = null
    private var isGroupChat: Boolean = false

    private/*  jsonObjectBody.put(KEY_DATA, jsonObjectData);
        jsonObjectBody.put(KEY_NOTIFICATION, jsonObjectData);
        jsonObjectBody.put(KEY_TO, mReceiverFirebaseToken);
*/ val validJsonBody: JSONObject
        @Throws(JSONException::class)
        get() {
            val data = JSONObject()
            val params = HashMap<String, Any>()


            data.put(KEY_TITLE, mUsername)
            data.put(KEY_TEXT, mMessage)
            data.put(KEY_USERNAME, mUsername)
            data.put(KEY_UID, mUid)
            data.put(KEY_FCM_TOKEN, mFirebaseToken)
            data.put("notifincationType", "chat")
            data.put("ChatTitle", mTitle)
            data.put("opponentChatId", mUid)
            data.put("sound", "default")

            data.put("priority", "high")
            data.put("body", mMessage)
            data.put("icon", "new")
            data.put("title", mTitle)
            data.put("click_action", "ChatActivity")
            data.put("other_key", true)
            data.put("badge", "1")
            data.put("content_available", true)
            data.put("sound", "default")

//            params["to"] = mReceiverFirebaseToken
//            params["title"] = mTitle
            params["sound"] = "default"

            params["data"] = data
            params["notification"] = data


            return JSONObject(params)

        }

    fun title(title: String): FcmNotificationBuilder {
        mTitle = title
        return this
    }

    fun message(message: String): FcmNotificationBuilder {
        mMessage = message
        return this
    }

    fun username(username: String): FcmNotificationBuilder {
        mUsername = username
        return this
    }

    fun uid(uid: String): FcmNotificationBuilder {
        mUid = uid
        return this
    }

    fun firebaseToken(firebaseToken: String): FcmNotificationBuilder {
        mFirebaseToken = firebaseToken
        return this
    }

    fun receiverFirebaseToken(receiverFirebaseToken: String): FcmNotificationBuilder {
        mReceiverFirebaseToken = receiverFirebaseToken
        return this
    }

    fun receiverFirebaseTokenGroup(receiverFirebaseToken: JSONArray): FcmNotificationBuilder {
        mReceiverFirebaseTokenGroup = receiverFirebaseToken
        return this
    }

    fun eventPayLoad(payLoadEvent: String): FcmNotificationBuilder {
        this.payLoadEvent = payLoadEvent
        return this
    }

    fun isGroupChatModule(isGroupChat: Boolean?): FcmNotificationBuilder {
        this.isGroupChat = isGroupChat!!
        return this
    }

    fun send() {
        var requestBody: RequestBody? = null
        try {
            requestBody = RequestBody.create(MEDIA_TYPE_JSON, validJsonBody.toString())
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val request = Request.Builder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .addHeader(AUTHORIZATION, AUTH_KEY)
                .url(FCM_URL)
                .post(requestBody)
                .build()

        val call = OkHttpClient().newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "onGetAllUsersFailure: " + e.message)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.e(TAG, "onResponse: " + response.body()?.string())
            }
        })
    }

    companion object {
        val MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8")
        private val TAG = "FcmNotificationBuilder"
        private val CONTENT_TYPE = "Content-Type"
        private val APPLICATION_JSON = "application/json"
        private val AUTHORIZATION = "Authorization"
        //""""""""""""""""" livewire live """"""""""""""" //
        // private static final String AUTH_KEY = "key=" + "AAAAQknvbWY:APA91bFkVuKKBs-Fs89CZvetn60a9J_JP46-PONqj0LYjEu6cvRcQW4BcVvZXCLLIU-EBzQmvNmA3ZMnNwIS8UEMN02pbcO6fK-08_auO4WJ1X7WUZ1BKVAoUyIlvNSj3tZRq6YvGjGm";


        //""""""""""""""""" livewire dev """"""""""""""" //
        private val AUTH_KEY = "key=" + "AAAABRhkfbo:APA91bEy7Iw0lp0wsjBYS42rFodoLtrMwPc4qhShoyHIaz6TtEK1uqSdXOXjp4S5adOodPnPK9vprzV3Jh_GBQMn-GW7YUeRYKRs69_V-05meQ2C1g60nDQW_QW7H1ZFr8lkZ-EvLk2A"


        private val FCM_URL = "https://fcm.googleapis.com/fcm/send"
        // json related keys
        private val KEY_TO = "to"
        private val KEY_NOTIFICATION = "notification"
        private val KEY_TITLE = "title"
        private val KEY_TEXT = "message"
        private val KEY_DATA = "data"
        private val KEY_USERNAME = "username"
        private val KEY_UID = "uid"
        private val KEY_FCM_TOKEN = "fcm_token"

        fun initialize(): FcmNotificationBuilder {
            return FcmNotificationBuilder()
        }
    }
}