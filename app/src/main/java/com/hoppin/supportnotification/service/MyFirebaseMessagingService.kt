package com.hoppin.supportnotification.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hoppin.R


class MyFirebaseMessagingService : FirebaseMessagingService() {
    //    String title = "Notification!";

    internal var CHANNEL_ID = "com.hours.labors"// The id of the channel.
    internal var title = ""


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        Log.e(TAG, "Body: " + remoteMessage!!.data.toString())
        Log.e(TAG, "Body1: $remoteMessage")


//        Bundle[{google.c.a.udt=0,
//            google.delivered_priority=high,
//            google.sent_time=1564824753299,
//            google.ttl=2419200,
//            google.original_priority=high,
//            gcm.notification.e=1,
//            google.c.a.c_id=1102247432812259427,
//            google.c.a.ts=1564824753,
//            gcm.notification.title=Hoopin,
//            gcm.n.e=1, from=52128343663,
//            google.message_id=0:1564824753485358%361a6db9361a6db9,
//            gcm.notification.body=Hello hoopin,
//            google.c.a.e=1,
//            gcm.notification.tag=campaign_collapse_key_1102247432812259427,
//            collapse_key=com.hoppin}]

        var intent: Intent? = null
        title = remoteMessage.data["title"].toString()
//        val sessionManager = Session(this)

        when (title) {

            "Login" -> {



            }

            "Tab" -> {



            }
        }


    }


    //    27/dec/2018 Changes
    private fun sendNotification(id: Int, title: String, intent: Intent?, messageBody: String, send: Boolean) {
        var pendingIntent: PendingIntent? = null
        val notificationBuilder: NotificationCompat.Builder
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_ONE_SHOT)
        val name = "MyChannal"// The user-visible name of the channel.
        val importance = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager.IMPORTANCE_HIGH
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        var mChannel: NotificationChannel? = null

        if (!send) {

            notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.hoopin_android)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)

        } else {
            notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                    // .setSmallIcon(R.drawable.icon_app_192_white)
                    .setColor(resources.getColor(R.color.colorPrimary))
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setSmallIcon(R.drawable.hoopin_android)
                    .setStyle(NotificationCompat.BigTextStyle().bigText(messageBody))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.hoopin_android)
            notificationBuilder.color = resources.getColor(R.color.colorPrimary)
        } else {
            notificationBuilder.setSmallIcon(R.drawable.hoopin_android)
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationManager.createNotificationChannel(mChannel)
        }
        notificationManager.notify(1, notificationBuilder.build())
    }

    private fun isAppOnForeground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return false
        val packageName = context.packageName
        for (appProcess in appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName == packageName) {
                return true
            }
        }
        return false
    }

    companion object {

        private val TAG = "MyFirebaseMsgService"
    }
}