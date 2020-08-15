package com.example.applicationtesting_wewillapp.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat

class Utils {
    companion object {
        const val APPID = "89bef124e939ec09060d867e013f0a4a"
        const val host = "https://api.openweathermap.org"
        const val Url_londImage = "https://openweathermap.org/img/w/"

        val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val timeFormatter: DateFormat = SimpleDateFormat("HH:mm")
    }

    fun getGson(): Gson? {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("M/d/yy hh:mm a")
        return gsonBuilder.create()
    }
}