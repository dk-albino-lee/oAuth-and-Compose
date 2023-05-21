package com.movingroot.kakaonaveroauthwithcompose.ui.feature

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Onboarding: Screen("onboarding_screen")
}
