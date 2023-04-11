package com.example.weather_app.data

data class WeatherParametrs(
    val city:String,
    val temp: Double,
    val temp_fl: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure:String,
    val humidity:String,
    val weather:String,
    val weather_desc:String,
    val clouds:String,
    val wind_sp:String,
    val visibility:String,
    val day_time:String,
    var time:String
)
