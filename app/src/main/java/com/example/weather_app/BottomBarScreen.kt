package com.example.weather_app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.weather_app.BottomBarScreen.Locations.route

sealed class BottomBarScreen(
    val route:String,
    val title:String,
    val icon:ImageVector
){
    object Locations: BottomBarScreen(
        route="locations",
        title="Locations",
        icon= Icons.Default.Star
    )
    object Forecast: BottomBarScreen(
        route="forecast",
        title="Forecast",
        icon=Icons.Default.Search
    )
    object Settings: BottomBarScreen(
        route="settings",
        title="Settings",
        icon=Icons.Default.Settings
    )
}
