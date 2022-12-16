package ktepin.penzasoft.dairy.model

import androidx.room.Database
import androidx.room.RoomDatabase
import ktepin.penzasoft.dairy.model.record.RecordDao
import ktepin.penzasoft.dairy.model.record.RecordEntity

@Database(entities = [RecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRecordDao(): RecordDao
}