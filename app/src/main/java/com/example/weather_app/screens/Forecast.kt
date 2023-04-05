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

const val API_KEY="ba4477674ac0fe2a90679194303ea1b4"

@Composable
fun ForecastScreen(city:String,context:Context){
    val state= remember {
        mutableStateOf("Updating")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = "FORECAST",
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "The weather in $city is ${state.value} now",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Button(onClick = { updateWeather(city,state,context) },
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()) {
                Text(text = "Update forecast")
            }
        }
    }
    updateWeather(city,state,context)
}

private fun updateWeather(city: String, state: MutableState<String>, context: Context){
    val url="https://api.weatherapi.com/v1/current.json"+
            "?key=$API_KEY&" +
            "q=$city" +
            "&aqi=no"
    val url1="https://api.openweathermap.org/data/2.5/forecast?id=524901&appid="+"$API_KEY&q=$city&aqi=no"//прогноз на 5 дней каждые 3 часа
    val url2="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+ API_KEY//сейчас погода
    val queue = Volley.newRequestQueue(context)
    val stringRequest=StringRequest(
        Request.Method.GET,
        url1,
    {
        responce ->
        state.value=responce
    },
    {
        error->
        Log.d("MyLog","Error: $error")
    }
    )
    queue.add(stringRequest)
}


@Composable
@Preview
fun ForecastScreenPreview() {
    //ForecastScreen("London",this)
}
