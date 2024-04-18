package com.example.socialmediaapp.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.socialmediaapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

private const val CHANNEL_ID = "my-channel"
private const val TAG = "Aman Gangwar"


@AndroidEntryPoint
class FirebaseService : FirebaseMessagingService() {


    @Inject
    lateinit var refDatabase: DatabaseReference

    @Inject
    lateinit var refStorage: StorageReference

    @Inject
    lateinit var mAuth: FirebaseAuth


    companion object {
        var sharedPref: SharedPreferences? = null
        var token: String?
            get() {
                return sharedPref?.getString("token", "")
            }
            set(value) {
                sharedPref?.edit()?.putString("token", value)?.apply()
            }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (mAuth.currentUser != null) {
            message.data["title"]?.let {
                val info = it.split("-")
                val title = info[0]
                if (mAuth.currentUser!!.uid != info[1]) {
                    // val intent = Intent(this, Messenger::class.java)
                    val notificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val notificationID = Random.nextInt()

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        createNotificationChannel(notificationManager)
                    }


                    val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message.data["message"])
                        .setSmallIcon(R.drawable.icon_message)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                        .setColor(resources.getColor(R.color.colorGreen, null))
                        .setAutoCancel(true)
                        //.setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(notificationID, notification)

                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "channelName"
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "My channel description"
                enableLights(true)
                enableVibration(true)
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
                lightColor = Color.GREEN
            }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        saveIntoSharedRef(token);
    }

    private fun saveIntoSharedRef(_token: String) {
        token = _token
    }
}