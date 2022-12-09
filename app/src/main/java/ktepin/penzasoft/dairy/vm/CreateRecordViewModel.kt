package ktepin.penzasoft.dairy.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ktepin.penzasoft.dairy.model.Record
import ktepin.penzasoft.dairy.util.SingleLiveEvent
import java.util.*

class CreateRecordViewModel : ViewModel() {
    private val _record = MutableLiveData<Record>().apply {
        value = Record.default()
    }
    val record: LiveData<Record> = _record

    fun setUri(uri:String){
        var value = _record.value
        if (value != null) {
            value.img = uri
            _record.postValue(value)
        }
    }

    fun update(title:String, descr:String){
        var value = _record.value
        if (value != null) {
            value.title = title
            value.description = descr
            _record.postValue(value)
        }
    }

    fun persist(){

    }
}