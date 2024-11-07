package pl.ztpproj2.lab2app.core.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.ksp.generated.module
import pl.ztpproj2.lab2app.core.di.AppModule

class ProductApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@ProductApp)
            modules(AppModule().module)
        }
    }
}