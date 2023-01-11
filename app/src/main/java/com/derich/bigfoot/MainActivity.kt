package com.derich.bigfoot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.derich.bigfoot.ui.composables.BigFutAppBar
import com.derich.bigfoot.ui.composables.HomeComposable
import com.derich.bigfoot.ui.composables.PhoneLoginUI
import com.derich.bigfoot.ui.data.AuthViewModel
import com.derich.bigfoot.ui.data.BigFootScreen
import com.derich.bigfoot.ui.data.ContributionsViewModel
import com.derich.bigfoot.ui.theme.BigFootTheme
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
            val contributionsViewModel: ContributionsViewModel by viewModels()
            val dataOrException = contributionsViewModel.data.value
            val authVm: AuthViewModel = viewModel()
            // Construct navigation graph here.
//            PhoneLoginUI(popUpScreen = { HomeComposable() }, viewModel = authVm)
            val navController = rememberNavController()
            BigFootTheme {
                Scaffold(
                    topBar = {
                        BigFutAppBar()
                    }
                ) {
                        innerPadding ->
                    val uiState by authVm.signUpState.collectAsState()
                    NavHost(
                        navController = navController,
                        startDestination = BigFootScreen.Login.name,
                        modifier = Modifier.padding(innerPadding)
                    )
                    {
                        composable(route = BigFootScreen.Login.name) {
                            PhoneLoginUI(
                                navigateToHome = { navController.navigate(BigFootScreen.Home.name) },
                                viewModel = authVm,
                                {
                                    navController.navigate(BigFootScreen.Login.name)
                                }
                            )
                        }
                        composable(route = BigFootScreen.Home.name) {
                            HomeComposable(dataOrException = dataOrException, viewModel = contributionsViewModel)
                        }
                    }

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