package com.example.weather_app

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weather_app.screens.ForecastScreen
import com.example.weather_app.screens.LocationsScreen
import com.example.weather_app.screens.SettingsScreen

@Composable
fun BottomNavGraph(navController: NavHostController,context: Context,packageName:String){
    NavHost(navController=navController,
        startDestination=BottomBarScreen.Locations.route
    ){
        composable(route=BottomBarScreen.Locations.route){
            LocationsScreen()
        }
        composable(route=BottomBarScreen.Forecast.route){
            ForecastScreen("Omsk",context,packageName)
        }
        composable(route=BottomBarScreen.Settings.route){
            SettingsScreen()
        }
    }
}