package com.example.weather_app

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(context:Context){
    val navController= rememberNavController( )
    Scaffold(bottomBar = { BottomBar(navController = navController)

    }) {
        BottomNavGraph(navController = navController,context)
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val screens= listOf(
        BottomBarScreen.Locations,
        BottomBarScreen.Forecast,
        BottomBarScreen.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currenDestination = navBackStackEntry?.destination
    
    
    BottomNavigation {
        screens.forEach { screen -> 
            AddItem(screen = screen,
                currentDestination = currenDestination,
                navController = navController)
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination:NavDestination?,
    navController: NavHostController
){
    BottomNavigationItem(label = {
        Text(text=screen.title)
    } , icon={
        Icon(imageVector = screen.icon,
        contentDescription = "Navigation Icon")
    },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    ) 
}