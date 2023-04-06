package com.example.weather_app.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.weather_app.data.WeatherParametrs

@Composable
fun TodayCard(currentWeatherState: MutableState<WeatherParametrs>){
    Column {

        Text(
            text = "FORECAST",
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "The weather in ${currentWeatherState.value.city} is ${currentWeatherState.value.temp} now",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}