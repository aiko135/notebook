package ktepin.penzasoft.dairy.model.record

class RecordRepository(private val recordDao: RecordDao) {

    suspend fun insert(r: RecordEntity){
        recordDao.insert(r)
    }

    suspend fun getAll() : List<RecordEntity>{
        return recordDao.getAll()
    }
}