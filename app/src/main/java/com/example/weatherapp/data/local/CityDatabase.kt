package com.example.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CityModel::class], version = 1, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

}
