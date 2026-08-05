package com.yohana.ntbinsider.ui.theme.navigation

sealed class NavPanel (val route: String) {
    object Home : NavPanel("home")
    object Favorite : NavPanel("favorite")
    object Profile : NavPanel("profile")
    object Detail : NavPanel("home/{tourId}") {
        fun createRoute(tourId: Int) = "home/$tourId"
    }
}