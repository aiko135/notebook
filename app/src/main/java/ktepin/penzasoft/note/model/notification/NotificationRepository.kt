package ktepin.penzasoft.note.model.notification

import android.content.Context
import com.google.gson.Gson

class NotificationRepository(
    private val context: Context
) {
    private val NOTIF_PREF_KEY : String = "notif";
    private val SHARED_PREFERENCES_NOTIF_FILE = "notification"

    fun registerNotification(notification: Notification) {
        val sharedPref =  context.getSharedPreferences(SHARED_PREFERENCES_NOTIF_FILE, Context.MODE_PRIVATE)
        //Сохранение
        val json : String  = Gson().toJson(notification)
        with(sharedPref.edit()){
            putString(NOTIF_PREF_KEY,json)
            commit()
        }
    }

    fun getNotification():Notification?{
        val sharedPref =  context.getSharedPreferences(SHARED_PREFERENCES_NOTIF_FILE, Context.MODE_PRIVATE)

        val found = sharedPref.getString(NOTIF_PREF_KEY,null)
        if(sharedPref.contains(NOTIF_PREF_KEY) && found != null){
            val notification : Notification = Gson().fromJson(found, Notification::class.java);
            return notification
        }
        else{
            return null
        }
    }

    fun clearNotification(){
        val sharedPref =  context.getSharedPreferences(SHARED_PREFERENCES_NOTIF_FILE, Context.MODE_PRIVATE)

        if(sharedPref.contains(NOTIF_PREF_KEY))
            with(sharedPref.edit()){
                remove(NOTIF_PREF_KEY)
                clear()
                commit()
            }
    }
}