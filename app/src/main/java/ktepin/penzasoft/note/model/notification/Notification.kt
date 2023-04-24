package ktepin.penzasoft.note.model.notification

import com.google.gson.annotations.SerializedName
import java.util.*

//Сохраняется в SharedPreferences
class Notification(
    @SerializedName("title")
    val title: String,
    @SerializedName("is24hour")
    val is24hour:Boolean,
    @SerializedName("hour")
    val hour:Int,
    @SerializedName("min")
    val min:Int

    //оставил этот вариант для дальнейшего развития приложения. Планируется добавления множества нотификейшенов в базу данных
//    @SerializedName("timestamp")
//    val timestamp: Date
)

