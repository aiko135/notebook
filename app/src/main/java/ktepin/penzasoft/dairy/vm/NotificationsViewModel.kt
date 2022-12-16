package ktepin.penzasoft.dairy.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ktepin.penzasoft.dairy.model.notification.Notification
import ktepin.penzasoft.dairy.model.notification.NotificationRepository
import org.koin.java.KoinJavaComponent.inject
import java.util.Date

class NotificationsViewModel : ViewModel() {

    private val notificationRepository : NotificationRepository by inject(NotificationRepository::class.java)

    private val _notification = MutableLiveData<Notification?>().apply {
        value = null
    }
    val notification: LiveData<Notification?> = _notification

    fun getNotification(){
        viewModelScope.launch(Dispatchers.IO) {
            val notification = notificationRepository.getNotification();
            _notification.postValue(notification)
        }
    }

    fun addNotification(notification: Notification){
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepository.clearNotification();
            notificationRepository.registerNotification(notification)
            getNotification()
        }
    }

    fun clearNotification(){
        viewModelScope.launch(Dispatchers.IO) {
            notificationRepository.clearNotification()
            getNotification()
        }
    }
}