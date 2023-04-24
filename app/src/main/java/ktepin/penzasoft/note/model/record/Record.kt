package ktepin.penzasoft.note.model.record

import java.util.Date

//Сохраняется в БД
data class Record (
    var title : String,
    var description : String,
    var img : String,
    var timestamp:Date,
    var geotag:LatLng?
){
    companion object {
        fun default(): Record {
            return Record("","","", Date(), null)
        }
    }
}