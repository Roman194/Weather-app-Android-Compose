package com.example.weather_app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_app.R
import com.example.weather_app.data.WeatherParametrs

@Composable
fun TodayCard(currentWeatherState: MutableState<WeatherParametrs>){
    var toCelsius:Int
    try{
        toCelsius=(currentWeatherState.value.temp.toDouble()-273.15).toInt()
    }catch (e:java.lang.NumberFormatException){
        toCelsius=0
    }
    Column {

        Text(
            text = "Right now",
            fontSize = MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Row {
            Image(
                painter = painterResource(id = R.drawable.clear_day),
                contentDescription = "logo picture"
            )

            Text(
                text = "${toCelsius} ℃",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}