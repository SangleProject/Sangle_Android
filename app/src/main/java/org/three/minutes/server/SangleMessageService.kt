package org.three.minutes.server

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.three.minutes.R
import org.three.minutes.home.ui.HomeActivity

class SangleMessageService : FirebaseMessagingService() {
    override fun onMessageReceived(rm: RemoteMessage) {
        if (rm.notification != null) {
            sendNotification(rm.notification?.title, rm.notification?.body)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // 새로운 토큰 생성 시 호출
        // 토큰을 서버에 보관하고 싶다면 여기서 서버 통신
        Log.e("New Token","get FCM New Token")
    }

    private fun sendNotification(title: String?, body: String?) {
        val messageTitle = title ?: return
        val messageBody = body ?: return
        val channelId = getString(R.string.FCM_Channel_title)

        // 알림 클릭시 해당 뷰로 이동
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setSound(defaultSound)
            .setContentIntent(pendingIntent)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,"SangleChannel",
            NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0,notificationBuilder.build())
    }
}