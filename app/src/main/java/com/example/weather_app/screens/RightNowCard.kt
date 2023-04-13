package com.example.weather_app.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
fun RightNowCard(currentWeatherState: MutableState<WeatherParametrs>,context:Context,packageName:String, onClickSync: ()-> Unit){
    var toCelsius:Int
    var toCelsius_FL:Int
    val weatherNow:String
    val weatherNowId:Int
    try{
        toCelsius=(currentWeatherState.value.temp-273.15).toInt()
        toCelsius_FL=(currentWeatherState.value.temp_fl-273.15).toInt()
    }catch (e:java.lang.NumberFormatException){
        toCelsius=0
        toCelsius_FL=0
    }
    val famous_Weather= listOf("clear","clouds","dust","extreme","fog","rain","snow","thunderstorm","wind","")
    if(currentWeatherState.value.weather.lowercase() in famous_Weather){
        if (currentWeatherState.value.weather.lowercase()=="wind" || currentWeatherState.value.weather.lowercase()=="dust"){
            weatherNow=currentWeatherState.value.weather.lowercase()
        }else{
            weatherNow=currentWeatherState.value.weather.lowercase().ifEmpty { "clear" }+"_"+currentWeatherState.value.day_time.ifEmpty { "d" }
        }
    }else{//IMO, unknown weather needs an attention of the user; in situations when state is just empty i prefer to show user the most default icon(clear_d)
        weatherNow="extreme_"+currentWeatherState.value.day_time.ifEmpty { "d" }
    }


    weatherNowId=context.getResources().getIdentifier(weatherNow,"drawable",packageName)



    Column {
        Row{
            Text(
                text = "Right now",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(6.dp)
            )

            IconButton(onClick = {
                onClickSync.invoke()
                Toast.makeText(
                    context,
                    "Weather data were successfully updated",
                    Toast.LENGTH_LONG
                ).show()
            }) {
                Icon(painter = painterResource(id = R.drawable.baseline_cloud_sync_24),
                contentDescription = "Sync icon",
                tint=Color.White,
                modifier = Modifier.size(28.dp))
                    //.padding(vertical = 2.dp))
            }
        }

        Row {
            Image(
                painter = painterResource(id = weatherNowId),
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