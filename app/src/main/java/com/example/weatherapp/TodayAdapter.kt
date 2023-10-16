package com.example.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.gms.gcm.Task

import org.json.JSONArray
import org.json.JSONObject

class TodayAdapter(private val hours: JSONArray): RecyclerView.Adapter<TodayAdapter.MyHolder>() {
    class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var hour: TextView = itemView.findViewById(R.id.w_time)
        var temp: TextView = itemView.findViewById(R.id.w_degree)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_today_weather, parent, false))
    }

    override fun getItemCount(): Int {
        return hours.length()
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val hour: JSONObject = hours.getJSONObject(position)
        val time = hour.getString("time")
        val temp = hour.getDouble("temp_c")
        holder.hour.text = time.substring(time.length-6)
        holder.temp.text = temp.toString()


    }

}