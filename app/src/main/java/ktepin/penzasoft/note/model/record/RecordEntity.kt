package ktepin.penzasoft.note.model.record

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "record")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "descr") val description: String,
    @ColumnInfo(name = "img") val img: String,
    @ColumnInfo(name = "udt") val timestamp: String,
    @ColumnInfo(name = "lat") val lat: Float,
    @ColumnInfo(name = "lng") val lng: Float
) {
    companion object {
        fun fromRecord(r: Record): RecordEntity {
            val lat: Float = r.geotag?.lat ?: (-1.0).toFloat()
            val lng: Float = r.geotag?.lng ?: (-1.0).toFloat()
            return RecordEntity(0, r.title, r.description, r.img, r.timestamp.toString(), lat, lng)
        }

        fun toRecord(re: RecordEntity): Record {
            var latLng: LatLng? = null
            if (re.lat > 0 && re.lng > 0)
                latLng = LatLng(re.lat, re.lng)
            return Record(re.title, re.description, re.img, Date(re.timestamp),latLng)
        }
    }
}

