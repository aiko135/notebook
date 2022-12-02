package ktepin.penzasoft.dairy

import android.app.Application
import ktepin.penzasoft.dairy.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        //Enable DI module
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}