package com.example.weatherapp.di

import android.app.Application
import android.content.Context
import android.location.LocationProvider
import androidx.room.Room
import com.example.weatherapp.common.Constant
import com.example.weatherapp.data.WeatherApi
import com.example.weatherapp.data.local.CityDao
import com.example.weatherapp.data.local.CityDatabase
import com.example.weatherapp.data.repository.LocalRepositoryImpl
import com.example.weatherapp.data.repository.LocationRepositoryImpl
import com.example.weatherapp.data.repository.WeatherCityRepositoryImpl
import com.example.weatherapp.domain.repository.LocalRepository
import com.example.weatherapp.domain.repository.LocationRepository
import com.example.weatherapp.domain.repository.WeatherCityRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // network module
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder().addInterceptor(logging)

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .baseUrl(Constant.BASE_URL_RETROFIT_API).build().create(WeatherApi::class.java)
    }

    // repository module
    @Provides
    @Singleton
    fun provideWeatherCityRepository(api: WeatherApi): WeatherCityRepository = WeatherCityRepositoryImpl(api)


    @Provides
    fun provideLocationProviderClient(application: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(@ApplicationContext appContext: Context, client: FusedLocationProviderClient): LocationRepository = LocationRepositoryImpl(appContext, client)

    @Provides
    @Singleton
    fun provideLocalRepository(cityDao: CityDao): LocalRepository = LocalRepositoryImpl(cityDao)

    // local module
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CityDatabase {
        return Room.databaseBuilder(context, CityDatabase::class.java, "cities.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun cityDao(database: CityDatabase): CityDao {
        return database.cityDao()
    }
}