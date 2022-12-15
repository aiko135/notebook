package ktepin.penzasoft.dairy

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import ktepin.penzasoft.dairy.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        //Enable DI module
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}