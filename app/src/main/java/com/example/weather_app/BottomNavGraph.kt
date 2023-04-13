package com.example.weather_app

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weather_app.data.CityParametres
import com.example.weather_app.screens.ForecastScreen
import com.example.weather_app.screens.LocationsScreen
import com.example.weather_app.screens.SettingsScreen

@Composable
fun BottomNavGraph(navController: NavHostController,context: Context,packageName:String,cityState:MutableState<CityParametres>){
    NavHost(navController=navController,
        startDestination=BottomBarScreen.Locations.route
    ){
        composable(route=BottomBarScreen.Locations.route){
            LocationsScreen(cityState,context)
        }
        composable(route=BottomBarScreen.Forecast.route){
            ForecastScreen(cityState,context,packageName)
        }
        composable(route=BottomBarScreen.Settings.route){
            SettingsScreen()
        }
    }
}