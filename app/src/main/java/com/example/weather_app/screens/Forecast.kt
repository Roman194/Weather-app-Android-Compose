package com.example.weather_app.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weather_app.BuildConfig
import com.example.weather_app.data.WeatherParametrs
import org.json.JSONArray
import org.json.JSONObject

const val API_KEY="ba4477674ac0fe2a90679194303ea1b4"

@Composable
fun ForecastScreen(city:String,context:Context){
    val forecastState= remember {
        mutableStateOf(listOf<WeatherParametrs>())
    }
    val currentWeatherState=remember{
        mutableStateOf(WeatherParametrs("Omsk", "Updating...","...","...","...","...",
            "...","...","...","...","...","..."))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column {
            TodayCard(currentWeatherState)

            Button(onClick = { updateWeather(city,forecastState,currentWeatherState,context) },
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()) {
                Text(text = "Update forecast")
            }
        }
    }
    updateWeather(city,forecastState,currentWeatherState,context)
}

private fun updateWeather(city: String, forecastState: MutableState<List<WeatherParametrs>>
                          ,currentWeatherState:MutableState<WeatherParametrs>, context: Context){
    val url="https://api.weatherapi.com/v1/current.json"+
            "?key=$API_KEY&" +
            "q=$city" +
            "&aqi=no"
    val url1="https://api.openweathermap.org/data/2.5/forecast?q=$city&appid="+"$API_KEY"//прогноз на 5 дней каждые 3 часа
    //val url2="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+ API_KEY//сейчас погода
    val queue = Volley.newRequestQueue(context)
    val stringRequest=StringRequest(
        Request.Method.GET,
        url1,
    {
        responce ->
        //val obj=JSONObject(responce)
        //val jbo=JSONArray(responce)
        val data_list= getWeather(responce)
        forecastState.value=data_list
        currentWeatherState.value=data_list[0]
        //state.value=jbo.getJSONObject(0).getString("temp")
        //state.value=obj.getJSONObject("main").getString("temp")
    },
    {
        error->
        Log.d("MyLog","Error: $error")
    }
    )
    queue.add(stringRequest)
}

private fun getWeather(response:String): List<WeatherParametrs>{
    if (response.isEmpty()) return listOf()
    val list=ArrayList<WeatherParametrs>()
    val mainObject=JSONObject(response)
    val city=mainObject.getJSONObject("city").getString("name")
    val hours=mainObject.getJSONArray("list")
    for (i in 0 until hours.length()){
        val item=hours[i] as JSONObject
        list.add(
            WeatherParametrs(
                city,
                item.getJSONObject("main").getString("temp"),
                item.getJSONObject("main").getString("feels_like"),
                //item.getJSONObject("main").getString("temp_min"),
                //item.getJSONObject("main").getString("temp_max"),
                item.getJSONObject("main").getString("pressure"),
                item.getJSONObject("main").getString("humidity"),
                item.getJSONArray("weather").getJSONObject(0).getString("main"),
                item.getJSONArray("weather").getJSONObject(0).getString("description"),
                item.getJSONObject("clouds").getString("all"),
                item.getJSONObject("wind").getString("speed"),
                item.getString("visibility"),
                item.getString("pop"),
                item.getString("dt_txt")
            )
        )
    }
    return list
}