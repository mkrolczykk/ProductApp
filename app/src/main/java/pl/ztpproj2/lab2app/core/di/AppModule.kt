package pl.ztpproj2.lab2app.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import pl.ztpproj2.lab2app.core.utils.DateDeserializer
import pl.ztpproj2.lab2app.features.domain.database.DatabaseHelper
import pl.ztpproj2.lab2app.features.domain.model.remote.ProductService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

const val BASE_URL = "http://10.0.2.2:8110" //Adres dla emulatora

@Module
@ComponentScan("pl.ztpproj2.lab2app")
class AppModule {

    @Single
    fun provideGson() = GsonBuilder()
        .registerTypeAdapter(Date::class.java, DateDeserializer())
        .create()

    @Single
    fun provideRetrofit(client: OkHttpClient, gson: Gson) = Retrofit.Builder()
        .callFactory(client)
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Single
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Single
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
    ) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Single
    fun provideProductsService(retrofit: Retrofit): ProductService =
        retrofit.create(ProductService::class.java)

    @Single
    fun provideDatabaseModule(context: Context) = Room.databaseBuilder(
        context,
        DatabaseHelper::class.java,
        "Database"
    ).build()

    @Single
    fun provideDatabaseModule(databaseHelper: DatabaseHelper) = databaseHelper.productDao()

    @Single
    fun getSharedPrefs(androidApplication: Application): SharedPreferences {
        return androidApplication.getSharedPreferences(
            "Timestamp",
            Context.MODE_PRIVATE
        )
    }

    @Single
    fun getSharedPrefsEdit(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }
}