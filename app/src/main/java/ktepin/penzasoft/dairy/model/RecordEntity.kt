package ktepin.penzasoft.dairy.model

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
    @ColumnInfo(name = "udt") val timestamp: String
) {
    companion object {
        fun fromRecord(r: Record): RecordEntity {
            return RecordEntity(0, r.title, r.description, r.img, r.timestamp.toString())
        }

        fun toRecord(re: RecordEntity): Record {
            return Record(re.title,re.description,re.img, Date(re.timestamp))
        }
    }
}

