package com.devlab.weather.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class NetworkWeather {
    private val API_KEY = "82120c83f5f8401484e165737220312"

    fun getData(
        city: String,
        context: Context,
        daysList: MutableState<List<WeatherModel>>,
        currentDay: MutableState<WeatherModel>,
    ) {
        val url =
            "https://api.weatherapi.com/v1/forecast.json?key=$API_KEY&q=$city&days=10&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val sRequest = StringRequest(Request.Method.GET, url, { response ->
            val list = getWeatherByDays(response)
            daysList.value = list
            currentDay.value = list[0]
        }, { error ->
            Log.e("Volley", error.toString())
        })
        queue.add(sRequest)
    }

    private fun getWeatherByDays(response: String): List<WeatherModel> {
        if (response.isEmpty()) return listOf()

        val mainObj = JSONObject(response)
        val days = mainObj.getJSONObject("forecast").getJSONArray("forecastday")
        val city = mainObj.getJSONObject("location").getString("name")
        val weatherModelList = ArrayList<WeatherModel>()

        for (i in 0 until days.length()) {
            val item = days[i] as JSONObject
            weatherModelList.add(
                WeatherModel(
                    city = city,
                    date = item.getString("date"),
                    currentTemp = "",
                    condition = item.getJSONObject("day").getJSONObject("condition")
                        .getString("text"),
                    icon = item.getJSONObject("day").getJSONObject("condition")
                        .getString("icon"),
                    maxTemp = item.getJSONObject("day").getString("maxtemp_c").toFloat().toInt()
                        .toString(),
                    minTemp = item.getJSONObject("day").getString("mintemp_c").toFloat().toInt()
                        .toString(),
                    hour = item.getJSONArray("hour").toString(),
                )
            )
        }

        weatherModelList[0] = weatherModelList[0].copy(
            date = mainObj.getJSONObject("current").getString("last_updated"),
            currentTemp = mainObj.getJSONObject("current").getString("temp_c").toFloat().toInt()
                .toString(),
        )
        return weatherModelList
    }

    fun getWeatherByHours(hours: String): List<WeatherModel> {
        if (hours.isEmpty()) return listOf()
        val weatherModelList = ArrayList<WeatherModel>()

        val hoursArray = JSONArray(hours)
        for (i in 0 until hoursArray.length()) {
            val item = hoursArray[i] as JSONObject
            weatherModelList.add(
                WeatherModel(
                    date = item.getString("time"),
                    currentTemp = item.getString("temp_c").toFloat().toInt().toString(),
                    condition = item.getJSONObject("condition").getString("text"),
                    icon = item.getJSONObject("condition").getString("icon"),
                )
            )
        }

        return weatherModelList
    }
}