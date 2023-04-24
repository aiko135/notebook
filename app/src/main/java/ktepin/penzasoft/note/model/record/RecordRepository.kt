package ktepin.penzasoft.note.model.record

class RecordRepository(private val recordDao: RecordDao) {

    suspend fun insert(r: RecordEntity){
        recordDao.insert(r)
    }

    suspend fun getAll() : List<RecordEntity>{
        return recordDao.getAll()
    }
}