package com.derich.bigfoot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.derich.bigfoot.ui.bottomnavigation.BottomNavigator
import com.derich.bigfoot.ui.bottomnavigation.NavigationGraph
import com.derich.bigfoot.ui.common.BigFutAppBar
import com.derich.bigfoot.ui.screens.home.ContributionsViewModel
import com.derich.bigfoot.ui.screens.loans.LoansViewModel
import com.derich.bigfoot.ui.screens.login.AuthViewModel
import com.derich.bigfoot.ui.screens.transactions.TransactionsViewModel
import com.derich.bigfoot.ui.theme.BigFootTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        var mainActivity: MainActivity? = null

        fun getInstance(): MainActivity? = mainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivity = this
        super.onCreate(savedInstanceState)
        setContent {
            //viewmodel handling all actions on contributions
            val contributionsViewModel: ContributionsViewModel by viewModels()
            val transactionsViewModel: TransactionsViewModel by viewModels()
            val loansVM: LoansViewModel by viewModels()
            //get data from firebase firestone
            val dataOrException = contributionsViewModel.data.value
            //login viewmodel handling all login activities
            val authVm: AuthViewModel = viewModel()

            FirebaseApp.initializeApp(/*context=*/this)
//            val firebaseAppCheck = FirebaseAppCheck.getInstance()
//            firebaseAppCheck.installAppCheckProviderFactory(
//                DebugAppCheckProviderFactory.getInstance()
//            )
            // Construct navigation graph here.
//            PhoneLoginUI(popUpScreen = { HomeComposable() }, viewModel = authVm)
            //navcontroller for the bottom navigation
            val bottomNavController = rememberNavController()
            BigFootTheme {
                Scaffold(
                    topBar = {
                        BigFutAppBar()
                    },
                    bottomBar = { BottomNavigator(bottomNavController) }
                ) {
                        innerPadding ->
                    NavigationGraph(
                        navController = bottomNavController,
                        dataOrException = dataOrException,
                        contViewModel = contributionsViewModel,
                        modifier = Modifier.padding(innerPadding),
                        transactionsViewModel = transactionsViewModel,
                        loansVM = loansVM,
                        authVm = authVm
                    )

                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        mainActivity = this
    }

    override fun onRestart() {
        super.onRestart()
        mainActivity = this
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivity = null
    }


}

@Preview
@Composable
fun MainPrev(){

}