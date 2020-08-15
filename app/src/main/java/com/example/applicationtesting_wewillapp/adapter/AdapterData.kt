package com.example.applicationtesting_wewillapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationtesting_wewillapp.R
import com.example.applicationtesting_wewillapp.model.Hourly
import com.example.applicationtesting_wewillapp.retrofit.Utils
import com.squareup.picasso.Picasso
import java.util.*

class AdapterData(var ct: Context, private var mDataWeatherX: List<Hourly>): RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_weather,parent,false
            )
        )
    }

    override fun getItemCount()= mDataWeatherX.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.temp.text = mDataWeatherX.get(position).temp.toString().substring(0,2)+" ํ"
        Log.d("Data",mDataWeatherX.get(position).weather[0].icon)
        Picasso.get().load(Utils.Url_londImage+mDataWeatherX.get(position).weather[0].icon+".png").into(holder.Image)
        var date =getDateTimeFromEpocLongOfSeconds(mDataWeatherX.get(position).dt.toLong())
        var time = date.toString().substring(11,16)
        holder.time.text = time
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
class ViewHolder(item : View): RecyclerView.ViewHolder(item){
    val time = item.findViewById<TextView>(R.id.time_item)
    val Image = item.findViewById<ImageView>(R.id.image_hourly)
    val temp = item.findViewById<TextView>(R.id.temp_item)
}