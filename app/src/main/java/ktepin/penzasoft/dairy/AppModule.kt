package ktepin.penzasoft.dairy

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

import ktepin.penzasoft.dairy.vm.HomeViewModel
import ktepin.penzasoft.dairy.vm.NotificationsViewModel
import ktepin.penzasoft.dairy.vm.DashboardViewModel
import ktepin.penzasoft.dairy.vm.CreateRecordViewModel
import ktepin.penzasoft.dairy.vm.CreateNotificationViewModel
import ktepin.penzasoft.dairy.util.PermissionManager
import ktepin.penzasoft.dairy.util.CameraManager
import org.koin.core.module.dsl.singleOf

val appModule = module {
    //vm
    viewModelOf(::HomeViewModel)
    viewModelOf(::NotificationsViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::CreateRecordViewModel)
    viewModelOf(::CreateNotificationViewModel)

    singleOf(::PermissionManager)
    singleOf(::CameraManager)
}