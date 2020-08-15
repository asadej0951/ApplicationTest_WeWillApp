package com.example.applicationtesting_wewillapp.retrofit

import com.example.applicationtesting_wewillapp.model.ResponseData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {
    @GET("/data/2.5/onecall?")
    fun openweathermap(@Query("lat")lat:Double,
                       @Query("lon")lon:Double,
                       @Query("exclude")exclude:String,
                       @Query("units")units:String
                       , @Query("appid")APPID:String)
            : Observable<ResponseData>
}