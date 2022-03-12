package com.example.weatherapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.example.weatherapp.common.Constant.FASTEST_UPDATE_INTERVAL_SECS
import com.example.weatherapp.common.Constant.UPDATE_INTERVAL_SECS
import com.example.weatherapp.domain.model.location.Location
import com.example.weatherapp.domain.repository.LocationRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val appContext: Context,
    private val client: FusedLocationProviderClient
) : LocationRepository {

    @SuppressLint("MissingPermission")
    override fun fetchLocation(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(UPDATE_INTERVAL_SECS)
            fastestInterval = TimeUnit.SECONDS.toMillis(FASTEST_UPDATE_INTERVAL_SECS)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY


        }
        val callBack = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                val location = locationResult.lastLocation
                val userLocation = Location(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    bearing = location.bearing
                )
                try {
                    this@callbackFlow.trySend(userLocation).isSuccess
                } catch (e: Exception) {
                }
            }
        }

        client.requestLocationUpdates(locationRequest, callBack, Looper.getMainLooper()
        ).addOnFailureListener { e ->
            close(e)
        }.addOnCompleteListener {
            client.removeLocationUpdates(callBack)
        }
        awaitClose {
            client.removeLocationUpdates(callBack)
        }
    }
}