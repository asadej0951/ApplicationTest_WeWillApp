package com.example.applicationtesting_wewillapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var mPresenterData = PresenterData()
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat :Double? = null
    private  var lon :Double? = null
    var exclude = "daily"
    var units = "metric"
    lateinit var mAdapterData:AdapterData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setLatLon()

    }

    private fun setLatLon() {
        if (ActivityCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        //รับค่าตำแหน่งปัจจุบัน
        fusedLocationClient.lastLocation.addOnSuccessListener(this){location ->
            if (location != null){
                lat = location.latitude
                lon = location.longitude
                Log.d("Data",lat.toString()+"/"+lon.toString())
                setAPI(lat!!,lon!!)
            }
        }
    }

    private fun setAPI(lat: Double, lon: Double) {
        mPresenterData.OpenDataPresenterRX(
                lat, lon,exclude,units,
                Utils.APPID,this::OpenDataNext,this::OpenDataError)
    }

    private fun OpenDataNext(responseData: ResponseData) {
        name_city.setText(responseData.timezone)
        temperature_today.setText(responseData.current.temp.toString().substring(0,2)+" ํ")
        Picasso.get().load(Utils.Url_londImage+responseData.current.weather[0].icon+".png").into(icon_Weather)
        description.setText(responseData.current.weather[0].description)
        humidity.setText("${responseData.current.humidity}% \nHumidity")
        wind_speed.setText("${responseData.current.wind_speed}% \nwind speed")
        var date = getDateTimeFromEpocLongOfSeconds(responseData.current.dt.toLong())
        var day = date.toString().substring(8,10)
        var Month = date.toString().substring(4,7)
        var Year = date.toString().substring(30)
        var time = date.toString().substring(11,16)
        Log.d("Data",date)
        DateTime.setText("${day} ${Month} ${Year},${time} ")

        mAdapterData = AdapterData(this,responseData.hourly)
        recycler_hourly.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = mAdapterData
            mAdapterData.notifyDataSetChanged()
        }
    }

    private fun OpenDataError(message:String) {
    }
    private fun getDateTimeFromEpocLongOfSeconds(epoc: Long): String? {
        try {
            val netDate = Date(epoc*1000)

            return netDate.toString()
        } catch (e: Exception) {
            return e.toString()
        }
    }
}