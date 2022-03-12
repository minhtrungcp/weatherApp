package com.example.weatherapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class CityModel(

    val name: String? = null,

    @PrimaryKey
    val id: Int? = null,
)

