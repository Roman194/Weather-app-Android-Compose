package com.example.weather_app.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_app.data.WeatherParametrs

@Composable
fun TodayCard(forecastState: MutableState<List<WeatherParametrs>>){
    var today:WeatherParametrs
    var toCelsius_Min:String
    var toCelsius_Max:String
    try{
        today= getWeatherToday(forecastState.value)

    }catch(e:java.lang.IndexOutOfBoundsException){
        today= WeatherParametrs("",0.0,0.0,0.0,0.0,"","","",
            "","","","","","2023-01-01")
    }
    toCelsius_Min=(today.temp_min-273.15).toInt().toString()
    toCelsius_Max=(today.temp_max-273.15).toInt().toString()



    Row {

        Text(
            text = "Today:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(6.dp)
        )
        Text(
            text = "${today.time}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(6.dp)
        )
        Text(
            text = "${toCelsius_Max}/${toCelsius_Min} ℃",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(6.dp)
        )

    }
}

private fun getWeatherToday(day: List<WeatherParametrs>):WeatherParametrs{
    var min_temp:Double
    var max_temp:Double
    var splitted_list:List<String>
    var splitted_list_armagedon:List<String>
    var sorted_day:WeatherParametrs

    splitted_list=day[0].time.split(" ")
    min_temp=day[0].temp
    max_temp=day[0].temp
    for(i in 1 until day.size){
        splitted_list_armagedon=day[i].time.split(" ")
        if(splitted_list[0]!=splitted_list_armagedon[0]) {
            break
        }
        if(min_temp>day[i].temp)
            min_temp=day[i].temp
        if(max_temp<day[i].temp)
            max_temp=day[i].temp
    }
    sorted_day=WeatherParametrs("",0.0,0.0,min_temp,max_temp,"",
        "","","","","","","",splitted_list[0])

    return sorted_day
}