package com.derich.bigfoot.ui.bottomnavigation

import android.util.Log
import android.widget.Toast
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.derich.bigfoot.R
import com.derich.bigfoot.ui.screens.account.AccountsComposable
import com.derich.bigfoot.ui.data.DataOrException
import com.derich.bigfoot.ui.screens.home.Contributions
import com.derich.bigfoot.ui.screens.home.ContributionsViewModel
import com.derich.bigfoot.ui.screens.home.HomeComposable
import com.derich.bigfoot.ui.screens.loans.LoansComposable
import com.derich.bigfoot.ui.screens.loans.LoansViewModel
import com.derich.bigfoot.ui.screens.login.AuthViewModel
import com.derich.bigfoot.ui.screens.login.PhoneLoginUI
import com.derich.bigfoot.ui.screens.transactions.TransactionsComposable
import com.derich.bigfoot.ui.screens.transactions.TransactionsViewModel

@Composable
fun BottomNavigator(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Transactions,
        BottomNavItem.Loans,
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
fun NavigationGraph(
    navController: NavHostController,
    dataOrException: DataOrException<List<Contributions>, Exception>,
    contViewModel: ContributionsViewModel,
    modifier: Modifier,
    transactionsViewModel: TransactionsViewModel,
    authVm: AuthViewModel,
    loansVM: LoansViewModel
) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route, modifier = modifier) {
        composable(BottomNavItem.Home.screen_route) {
            if (authVm.authState.currentUser != null){
                HomeComposable(dataOrException = dataOrException,
                    viewModel = contViewModel)
            }
            else {
                NavigateToLogin(navController = navController)
            }
        }
        composable(BottomNavItem.Loans.screen_route) {
            if (authVm.authState.currentUser != null){
                LoansComposable(loansViewModel = loansVM)
            }
            else {
                NavigateToLogin(navController = navController)
            }
        }
        composable(BottomNavItem.Transactions.screen_route) {
            if (authVm.authState.currentUser != null){
                TransactionsComposable(transactionsViewModel = transactionsViewModel)
            }
            else {
                NavigateToLogin(navController = navController)
            }
        }
        composable(BottomNavItem.Login.screen_route) {
            PhoneLoginUI(navigateToHome = {
                navController.navigate(BottomNavItem.Home.screen_route)
                navController.clearBackStack(0)
            }, viewModel = authVm,
                {
                    authVm.resetAuthState()
            })
        }
        composable(BottomNavItem.Account.screen_route) {
            if (authVm.authState.currentUser != null){
                AccountsComposable(authViewModel = authVm,
                    navController = navController)
                Log.e("Account Activity", "already loggedin")
            }
            else {
                NavigateToLogin(navController = navController)
                Toast.makeText(LocalContext.current, "Please login to continue", Toast.LENGTH_SHORT).show()
                Log.e("Account Activity", "NOT loggedin")
            }
        }
    }
}

@Composable
fun NavigateToLogin(navController: NavController) {

    LaunchedEffect(key1 = "navigateToLogin") {
        navController.navigate(BottomNavItem.Login.screen_route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}