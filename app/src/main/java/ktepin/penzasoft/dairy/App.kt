package ktepin.penzasoft.dairy

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    private val JOB_PERIOD : Long = 10*1000 //period 40 sec

    override fun onCreate() {
        super.onCreate()

        //Use AlarmManager
        //TODO setup it also with RECEIVE_BOOT_COMPLETED
        val alarmMgr = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(this, AlarmBroadcastReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }
        alarmMgr.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + JOB_PERIOD,
            JOB_PERIOD,
            alarmIntent
        )


        //Enable DI module
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}