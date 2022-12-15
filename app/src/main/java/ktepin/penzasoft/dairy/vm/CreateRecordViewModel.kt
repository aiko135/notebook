package ktepin.penzasoft.dairy.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ktepin.penzasoft.dairy.model.Record
import ktepin.penzasoft.dairy.model.RecordEntity
import ktepin.penzasoft.dairy.model.RecordRepository
import ktepin.penzasoft.dairy.util.SingleLiveEvent
import org.koin.java.KoinJavaComponent.inject
import java.util.*

class CreateRecordViewModel : ViewModel() {
    private val recordRepository: RecordRepository by inject(RecordRepository::class.java)

    private val _record = MutableLiveData<Record>().apply {
        value = Record.default()
    }
    val record: LiveData<Record> = _record

    fun setUri(uri: String) {
        var value = _record.value
        if (value != null) {
            value.img = uri
            _record.postValue(value)
        }
    }

    fun update(title: String, descr: String) {
        var value = _record.value
        if (value != null) {
            value.title = title
            value.description = descr
            _record.postValue(value)
        }
    }

    fun persist() {
        viewModelScope.launch(Dispatchers.IO) {
            _record.value?.let {
                val newRec : Record = _record.value!!
                if(newRec.title != "" && newRec.description != "")
                    recordRepository.insert(RecordEntity.fromRecord(it))
            }

        }
    }
}