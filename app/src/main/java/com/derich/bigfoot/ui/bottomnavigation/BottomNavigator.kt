@file:OptIn(ExperimentalCoroutinesApi::class)

package com.derich.bigfoot.ui.bottomnavigation

import android.util.Log
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.derich.bigfoot.ui.common.composables.CircularProgressBar
import com.derich.bigfoot.ui.screens.account.AccountsComposable
import com.derich.bigfoot.ui.screens.home.ContributionsViewModel
import com.derich.bigfoot.ui.screens.home.HomeComposable
import com.derich.bigfoot.ui.screens.home.MemberDetails
import com.derich.bigfoot.ui.screens.loans.LoansComposable
import com.derich.bigfoot.ui.screens.loans.LoansViewModel
import com.derich.bigfoot.ui.screens.login.AuthViewModel
import com.derich.bigfoot.ui.screens.transactions.TransactionsComposable
import com.derich.bigfoot.ui.screens.transactions.TransactionsViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi

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
    contViewModel: ContributionsViewModel,
    modifier: Modifier,
    transactionsViewModel: TransactionsViewModel,
    authVm: AuthViewModel,
    loansVM: LoansViewModel
) {
    val allMemberInfo: List<MemberDetails>? = contViewModel.memberData.value.data
//    var allMemberInfo by remember { mutableStateOf(List<MemberDetails>) }
    val memberDetails: MemberDetails
    if (allMemberInfo != null){
    if (allMemberInfo.isNotEmpty()){
        memberDetails = getMemberData(allMemberInfo)!!
        NavHost(navController, startDestination = BottomNavItem.Home.screen_route, modifier = modifier) {
            composable(BottomNavItem.Home.screen_route) {
                HomeComposable(viewModel = contViewModel, specificMemberDetails = memberDetails, allMembersInfo = allMemberInfo)
            }

            composable(BottomNavItem.Transactions.screen_route) {
                TransactionsComposable(transactionsViewModel = transactionsViewModel,
                        memberInfo = memberDetails)
            }
            composable(BottomNavItem.Loans.screen_route) {
                    LoansComposable(loansViewModel = loansVM,
                        memberInfo = memberDetails)
            }
            composable(BottomNavItem.Account.screen_route) {
                AccountsComposable(authViewModel = authVm,
                    memberInfo = memberDetails)
            }
        }
    }
    else{
        CircularProgressBar(
            isDisplayed = contViewModel.loadingMemberDetails.value
        )
    }
    }
    else{
        ErrorScreen(contViewModel.memberData.value.e.toString())
    }

}

@Composable
fun ErrorScreen(e: String) {
    Text(text = e)
    Log.e("Home", e)
}

fun getMemberData(memberInfo: List<MemberDetails>): MemberDetails? {
    var memberDets: MemberDetails? = null
    memberInfo.forEach {memberDetails ->
        if (memberDetails.phoneNumber == FirebaseAuth.getInstance().currentUser!!.phoneNumber){
            memberDets = memberDetails
            return memberDetails
        }
    }
    return memberDets
}