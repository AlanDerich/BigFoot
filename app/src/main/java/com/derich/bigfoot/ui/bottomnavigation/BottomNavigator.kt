package com.derich.bigfoot.ui.bottomnavigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.derich.bigfoot.R
import com.derich.bigfoot.ui.composables.AccountsScreen
import com.derich.bigfoot.ui.composables.LoansScreen
import com.derich.bigfoot.ui.composables.TransactionsScreen
import com.derich.bigfoot.ui.data.BigFootScreen

@Composable
fun BottomNavigator(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Loans,
        BottomNavItem.Transactions,
        BottomNavItem.Account
    )
    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_200),
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
@Composable
fun NavigationGraph(navController: NavHostController, ) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            BigFootScreen.Home
        }
        composable(BottomNavItem.Loans.screen_route) {
            LoansScreen()
        }
        composable(BottomNavItem.Transactions.screen_route) {
            TransactionsScreen()
        }
        composable(BottomNavItem.Account.screen_route) {
            AccountsScreen()
        }
    }
}