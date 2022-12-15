package ktepin.penzasoft.dairy.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDao {
    @Query("SELECT * FROM record LIMIT 100")
    fun getAll(): List<RecordEntity>

    @Insert
    fun insert(rec: RecordEntity)
}