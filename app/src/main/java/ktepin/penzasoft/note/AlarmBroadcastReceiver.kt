package ktepin.penzasoft.note

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ktepin.penzasoft.note.model.notification.NotificationRepository
import org.koin.java.KoinJavaComponent
import java.text.SimpleDateFormat
import java.util.*


class AlarmBroadcastReceiver : BroadcastReceiver() {
    private val CHANNEL_ID = "Channel_1"

    private val notificationRepository : NotificationRepository by KoinJavaComponent.inject(
        NotificationRepository::class.java
    )

    override fun onReceive(ctx: Context?, intent: Intent?) {
        Log.i("AlarmBroadcastReceiver", "Recived")

        if(ctx != null){
            val notification = notificationRepository.getNotification()
            if(notification != null){
                val calendar = Calendar.getInstance()
                var hourFormat = SimpleDateFormat("hh")
                if(notification.is24hour)
                    hourFormat = SimpleDateFormat("HH")
                val hour : String = hourFormat.format(calendar.time)
                if (hour.toInt() > notification.hour){
                    showNotification(ctx, notification.title)
                    notificationRepository.clearNotification()
                }
                if(hour.toInt() == notification.hour){
                    val minFormat = SimpleDateFormat("mm")
                    val min : String = minFormat.format(calendar.time)
                    if(min.toInt() >= notification.min){
                        showNotification(ctx, notification.title)
                        notificationRepository.clearNotification()
                    }
                }
            }
        }
    }

    private fun showNotification(ctx:Context, text:String){
        val mNotificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Alarm notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "Custom notification"
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel)
            }
        }

        val builder = NotificationCompat.Builder(ctx, CHANNEL_ID)
            .setContentTitle("My notification")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentText(ctx.getText(R.string.notification_header))
            .setSound(Uri.parse("android.resource://"+ctx.packageName+"/"+R.raw.notification))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(text)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(ctx)) {
            // notificationId is a unique int for each notification that you must define
            notify(0, builder.build())
        }

    }
}