package ktepin.penzasoft.dairy.model

import java.util.Date

data class Record (
    var title : String,
    var description : String,
    var img : String,
    var timestamp:Date
){
    companion object {
        fun default():Record{
            return Record("","","", Date())
        }
    }
}