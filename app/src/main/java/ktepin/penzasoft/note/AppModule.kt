package ktepin.penzasoft.note

import androidx.room.Room
import ktepin.penzasoft.note.model.AppDatabase
import ktepin.penzasoft.note.model.Config
import ktepin.penzasoft.note.model.notification.NotificationRepository
import ktepin.penzasoft.note.model.record.RecordDao
import ktepin.penzasoft.note.model.record.RecordRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

import ktepin.penzasoft.note.vm.HomeViewModel
import ktepin.penzasoft.note.vm.NotificationsViewModel
import ktepin.penzasoft.note.vm.DashboardViewModel
import ktepin.penzasoft.note.vm.CreateRecordViewModel
import ktepin.penzasoft.note.util.PermissionManager
import ktepin.penzasoft.note.util.CameraManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf

val appModule = module {
    //vm
    viewModelOf(::HomeViewModel)
    viewModelOf(::NotificationsViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::CreateRecordViewModel)

    singleOf(::PermissionManager)
    singleOf(::CameraManager)

    //TOD guide https://www.geeksforgeeks.org/how-to-build-a-simple-note-android-app-using-mvvm-and-room-database/
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            Config.DATABASE_NAME
        ).build()
    }

    single<RecordDao> {
        val database = get<AppDatabase>()
        database.getRecordDao()
    }

    /* в данном случае репозиторий - ещё один уровень абстракции.
    Нужен если мы захотим использовать разные DAO в одном запросе (запрос в несколько таблиц).
    в данном примере это не очевидно*/
    single<RecordRepository> {
        val dao = get<RecordDao>()
        RecordRepository(dao)
    }

    single<NotificationRepository> { NotificationRepository(androidApplication()) }
}