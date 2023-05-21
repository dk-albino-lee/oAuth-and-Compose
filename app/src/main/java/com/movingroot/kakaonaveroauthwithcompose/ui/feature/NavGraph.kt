package com.movingroot.kakaonaveroauthwithcompose.ui.feature

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.movingroot.kakaonaveroauthwithcompose.ui.feature.onboarding.OnboardingScreen
import com.movingroot.kakaonaveroauthwithcompose.ui.feature.splash.SplashScreen

@Composable
fun SetNavGraph(controller: NavHostController) {
    NavHost(
        navController = controller,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = controller)
        }
        composable(route = Screen.Onboarding.route) {
            OnboardingScreen(navController = controller)
        }
    }
}