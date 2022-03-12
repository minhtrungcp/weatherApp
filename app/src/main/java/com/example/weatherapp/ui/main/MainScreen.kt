package com.example.weatherapp.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.favorite_city.FavoriteCityScreen
import com.example.weatherapp.ui.search_weather_city.WeatherSearchScreen

@Composable
fun MainScreen(
    navController: NavHostController
) {
    val bottomBarNav = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(bottomBarNav) },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Navigation(navController = bottomBarNav, navController)
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, mainNav: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationItem.SearchCity.route
    ) {
        composable(
            route = NavigationItem.SearchCity.route
        ) {
            WeatherSearchScreen(navController = mainNav)
        }
        composable(
            route = NavigationItem.FavoriteCity.route
        ) {
            FavoriteCityScreen(navController = mainNav)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem.SearchCity,
        NavigationItem.FavoriteCity,
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                selected = item.route == currentRoute,
                label = { Text(text = item.title) },
                icon = {

                },
                alwaysShowLabel = true,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.4f),
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Weather App",
                    modifier = Modifier.align(Alignment.Center),
                )
            }
        }
    )
}

sealed class NavigationItem(
    var route: String,
    var title: String,
) {
    object SearchCity : NavigationItem("searchCity", "Search City")
    object FavoriteCity : NavigationItem("FavoriteCity", "Favorite City")
}
