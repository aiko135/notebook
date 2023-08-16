package ktepin.penzasoft.note.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ktepin.penzasoft.note.model.record.LatLng
import ktepin.penzasoft.note.model.record.Record
import ktepin.penzasoft.note.model.record.RecordEntity
import ktepin.penzasoft.note.model.record.RecordRepository
import org.koin.java.KoinJavaComponent.inject

class CreateRecordViewModel : ViewModel() {
    private val recordRepository: RecordRepository by inject(RecordRepository::class.java)

    private val _record = MutableLiveData<Record>().apply {
        value = Record.default()
    }
    val record: LiveData<Record> = _record

    fun setUri(uri: String) {
        _record.value?.let {
            it.img = uri
            _record.postValue(it)
        }
    }

    fun setGeoTag(lat:Float, lng:Float){
        _record.value?.let {
            it.geotag = LatLng(lat, lng)
            _record.postValue(it)
        }
    }

    fun update(title: String, descr: String) {
        _record.value?.let {
            it.title = title
            it.description = descr
            _record.postValue(it)
        }
    }

    fun persist() {
        viewModelScope.launch(Dispatchers.IO) {
            _record.value?.let {
                if(it.title != "")
                    recordRepository.insert(RecordEntity.fromRecord(it))
            }

        }
    }
}