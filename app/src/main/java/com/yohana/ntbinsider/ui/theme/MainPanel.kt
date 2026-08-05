package com.yohana.ntbinsider.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yohana.ntbinsider.R
import com.yohana.ntbinsider.ui.theme.navigation.NavItem
import com.yohana.ntbinsider.ui.theme.navigation.NavPanel
import com.yohana.ntbinsider.ui.theme.panel.AboutPanel
import com.yohana.ntbinsider.ui.theme.panel.DetailPanel
import com.yohana.ntbinsider.ui.theme.panel.FavoritePanel
import com.yohana.ntbinsider.ui.theme.panel.HomePanel

@Composable
fun MainPanel (modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != NavPanel.Detail.route) {
                BottomBar(navController, currentRoute)
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(snackbarData = data, shape = RoundedCornerShape(8.dp))
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavPanel.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavPanel.Home.route) {
                HomePanel(navController, scaffoldState)
            }
            composable(
                route = NavPanel.Detail.route,
                arguments = listOf(
                    navArgument("tourId") { type = NavType.IntType }
                )
            ) {
                val tourismId = it.arguments?.getInt("tourId") ?: 0
                DetailPanel(tourismId, navController, scaffoldState)
            }
            composable(NavPanel.Favorite.route) {
                FavoritePanel(navController, scaffoldState)
            }
            composable(NavPanel.Profile.route) {
                AboutPanel()
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String?,
) {
    val navigationItems = listOf(
        NavItem(
            title = stringResource(R.string.home),
            icon = Icons.Rounded.Home,
            panel = NavPanel.Home
        ),
        NavItem(
            title = stringResource(R.string.favorite),
            icon = Icons.Rounded.Favorite,
            panel = NavPanel.Favorite
        ),
        NavItem(
            title = stringResource(R.string.profile),
            icon = Icons.Rounded.Person,
            panel = NavPanel.Profile
        ),
    )

    BottomNavigation(backgroundColor = Color.White) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    val contentDescription = when (item.panel) {
                        is NavPanel.Profile -> "about_page"
                        else -> item.title
                    }
                    Icon(
                        imageVector = item.icon,
                        contentDescription = contentDescription
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.panel.route,
                selectedContentColor = MaterialTheme.colors.primaryVariant,
                unselectedContentColor = Color.Gray,
                onClick = {
                    navController.navigate(item.panel.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}