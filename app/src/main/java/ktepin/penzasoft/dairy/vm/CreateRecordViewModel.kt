package ktepin.penzasoft.dairy.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ktepin.penzasoft.dairy.model.record.LatLng
import ktepin.penzasoft.dairy.model.record.Record
import ktepin.penzasoft.dairy.model.record.RecordEntity
import ktepin.penzasoft.dairy.model.record.RecordRepository
import org.koin.java.KoinJavaComponent.inject
import kotlin.math.ln

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

    fun setGeoTag(lat:Float, lng:Float){
        var value = _record.value
        if (value != null) {
            value.geotag = LatLng(lat, lng)
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
                if(newRec.title != "")
                    recordRepository.insert(RecordEntity.fromRecord(it))
            }

        }
    }
}