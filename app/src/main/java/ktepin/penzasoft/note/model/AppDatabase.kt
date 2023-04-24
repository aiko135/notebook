package ktepin.penzasoft.note.model

import androidx.room.Database
import androidx.room.RoomDatabase
import ktepin.penzasoft.note.model.record.RecordDao
import ktepin.penzasoft.note.model.record.RecordEntity

@Database(entities = [RecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getRecordDao(): RecordDao
}