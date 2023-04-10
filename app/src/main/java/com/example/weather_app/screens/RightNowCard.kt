package com.example.weather_app.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_app.R
import com.example.weather_app.data.WeatherParametrs

@Composable
fun RightNowCard(currentWeatherState: MutableState<WeatherParametrs>){
    var toCelsius:Int
    var toCelsius_FL:Int
    try{
        toCelsius=(currentWeatherState.value.temp-273.15).toInt()
        toCelsius_FL=(currentWeatherState.value.temp_fl-273.15).toInt()
    }catch (e:java.lang.NumberFormatException){
        toCelsius=0
        toCelsius_FL=0
    }

    Column {

        Text(
            text = "Right now",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(6.dp)
        )
        Row {
            Image(
                painter = painterResource(id = R.drawable.clear_day),
                contentDescription = "logo picture"
            )
            Column{
                Text(
                    text = "${toCelsius} ℃",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "${currentWeatherState.value.weather_desc}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Column {
                Text(
                    text = "Feels like: ${toCelsius_FL} ℃",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Pressure: ${currentWeatherState.value.pressure} hPa",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Humidity: ${currentWeatherState.value.humidity} %",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Wind: ${currentWeatherState.value.wind_sp} m/s",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Visibility: ${currentWeatherState.value.visibility} m",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

            }

        }

    }
}