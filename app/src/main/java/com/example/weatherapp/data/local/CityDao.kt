package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {

    // insert new city
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(city : CityModel) : Long

    // Get all cities
    @Query("select * from city_table ")
    suspend fun getAllCities(): List<CityModel>

    // Get size cities
    @Query("select COUNT(*) FROM city_table")
    suspend fun getSizeCities(): Long

    // Delete all cities
    @Query("delete from city_table")
    suspend fun deleteAllCities()

    // Delete city by id
    @Query("delete from city_table where id =:id")
    suspend fun deleteCity(id : Int)

}