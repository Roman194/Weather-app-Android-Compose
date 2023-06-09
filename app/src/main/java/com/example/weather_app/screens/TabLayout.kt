package com.example.weather_app.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_app.data.WeatherParametrs
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(forecastState:MutableState<List<WeatherParametrs>>,context: Context,packageName:String,UTC:String){
    val tabList= listOf("In next 24 hours","In next 5 days")
    val pagerState= rememberPagerState()
    val tabIndex=pagerState.currentPage
    val courutineScope= rememberCoroutineScope()

    Column(modifier = Modifier.clip(RoundedCornerShape(6.dp))) {
        TabRow(selectedTabIndex = tabIndex,
            indicator = {pos->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState,pos)
                        )
            },
        backgroundColor = Color(244, 209, 68, 255)
        ) {
            tabList.forEachIndexed{index,text->
                Tab(selected = false,
                    onClick = {
                    courutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text={
                    Text(text = text)
                })
                    

            }
        }
        HorizontalPager(count = tabList.size,
                        state = pagerState,
                        modifier = Modifier.weight(1.0f)) {index->
                    val list=when(index){
                        0->getWeatherByHours(forecastState.value,UTC)
                        else->getWeatherByDays(forecastState.value)
                    }
            //Log.d("MyLog","Response:$list")
            MainList(list,context,packageName)

        }
    }
}

private fun getWeatherByHours(hours:List<WeatherParametrs>,UTC:String):List<WeatherParametrs>{
    if (hours.isEmpty()) return listOf()
    val splitted_list: List<String>
    var splitted_list_current:List<String>
    val sorted_hours=ArrayList<WeatherParametrs>()
    var right_Time:List<String>
    var right_Time_UTC:Int

    splitted_list=hours[0].time.split(" ")
    right_Time=splitted_list[1].split(":")
    right_Time_UTC=right_Time[0].toInt()+UTC.toInt()
    if (right_Time_UTC>23) right_Time_UTC%=24
    else{
        if (right_Time_UTC<0) right_Time_UTC=24+right_Time_UTC
    }
    sorted_hours.add(WeatherParametrs("",hours[0].temp,hours[0].temp_fl,0.0,0.0,"","",
        hours[0].weather,"","","","",hours[0].day_time,right_Time_UTC.toString()+":"+right_Time[1]))


    for(i in 1 until hours.size){
        splitted_list_current=hours[i].time.split(" ")
        if(splitted_list[1]==splitted_list_current[1]){
            break
        }
        right_Time=splitted_list_current[1].split(":")
        right_Time_UTC=right_Time[0].toInt()+UTC.toInt()
        if (right_Time_UTC>23) right_Time_UTC%=24
        else{
            if (right_Time_UTC<0) right_Time_UTC=24+right_Time_UTC
        }
        sorted_hours.add(WeatherParametrs("",hours[i].temp,hours[i].temp_fl,0.0,0.0,"","",
            hours[i].weather,"","","","",hours[i].day_time,right_Time_UTC.toString()+":"+right_Time[1]))
    }
    return sorted_hours
}

private fun getWeatherByDays(days:List<WeatherParametrs>):List<WeatherParametrs>{
    if(days.isEmpty()) return listOf()
    var min_temp:Double
    var max_temp:Double
    var splitted_list:List<String>
    var splitted_list_current:List<String>
    val sorted_days=ArrayList<WeatherParametrs>()
    var checker=true

    splitted_list=days[0].time.split(" ")
    min_temp=days[0].temp
    max_temp=days[0].temp
    for(i in 1 until days.size){
        splitted_list_current=days[i].time.split(" ")
        if(splitted_list[0]!=splitted_list_current[0]){
            if(checker)
                checker=false
            else{
                sorted_days.add(
                    WeatherParametrs("",0.0,0.0,min_temp,max_temp,"","",days[i-1].weather//not correct a bit
                        ,"","","","","d",splitted_list[0])
                )
            }
            splitted_list=splitted_list_current
            min_temp=days[i].temp
            max_temp=days[i].temp
        }else{
            if(min_temp>days[i].temp)
                min_temp=days[i].temp
            if(max_temp<days[i].temp)
                max_temp=days[i].temp
        }


    }



    return sorted_days
}

@Composable
fun MainList(list:List<WeatherParametrs>,context: Context,packageName: String){
    LazyColumn(modifier = Modifier.fillMaxSize() ){
        itemsIndexed(list){
                _,item-> ItemList(item,context,packageName)
        }
    }
}

@Composable
fun ItemList(item:WeatherParametrs,context: Context,packageName: String){


    var toCelsius:String
    var toCelsius_FL:String
    var toCelsius_Min:String
    var toCelsius_Max:String
    val weatherNow:String
    val weatherNowId:Int

    try{
        toCelsius=(item.temp-273.15).toInt().toString()
        toCelsius_FL=(item.temp_fl-273.15).toInt().toString()
        //Log.d("Log","${item.temp_fl}")

    }catch (e:java.lang.NumberFormatException){
        toCelsius="0"
        toCelsius_FL="0"
    }

    try{
        if(item.temp_min!=item.temp_max){
            toCelsius_Min=(item.temp_min-273.15).toInt().toString()
            toCelsius_Max=(item.temp_max-273.15).toInt().toString()
        }else{
            toCelsius_Min=""
            toCelsius_Max=""
        }

    }catch(e:java.lang.NumberFormatException){
        toCelsius_Min=""
        toCelsius_Max=""
    }

    weatherNow=item.weather.lowercase().ifEmpty { "clear" }+"_"+item.day_time.ifEmpty { "d" }
    weatherNowId=context.getResources().getIdentifier(weatherNow,"drawable",packageName)

    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 0.5.dp,
        color = MaterialTheme.colors.surface,
        modifier = Modifier.fillMaxWidth()
        //contentColor = Color(0,0,0,255)
    ) {
        Row {


            Text(
                text = item.time,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(6.dp)
            )
            Image(
                painter = painterResource(weatherNowId),
                contentDescription = "weather_logo",
                modifier = Modifier.size(52.dp, 48.dp)
            )
            Text(
                text = "${item.weather}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(6.dp)
            )
            Text(
                text = "${toCelsius_Max.ifEmpty{toCelsius}}/${toCelsius_Min.ifEmpty {toCelsius_FL}} ℃",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(6.dp)
            )
        }

    }
}