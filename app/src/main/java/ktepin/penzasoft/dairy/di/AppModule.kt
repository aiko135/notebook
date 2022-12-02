package ktepin.penzasoft.dairy.di
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

import ktepin.penzasoft.dairy.vm.HomeViewModel
import ktepin.penzasoft.dairy.vm.NotificationsViewModel
import ktepin.penzasoft.dairy.vm.DashboardViewModel
import ktepin.penzasoft.dairy.vm.CreateRecordViewModel
import ktepin.penzasoft.dairy.util.PermissionManager
import org.koin.core.module.dsl.singleOf

val appModule = module {
    //vm
    viewModelOf(::HomeViewModel)
    viewModelOf(::NotificationsViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::CreateRecordViewModel)

    singleOf(::PermissionManager)
}