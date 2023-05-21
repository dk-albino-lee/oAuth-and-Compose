package com.movingroot.kakaonaveroauthwithcompose.ui.feature.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.*
import com.movingroot.kakaonaveroauthwithcompose.R
import com.movingroot.kakaonaveroauthwithcompose.ui.feature.Screen

@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = viewModel()
) {
    ObserveNavigationState(navController, viewModel)

    Box(modifier = Modifier.fillMaxSize()) {
        ComposeLottieAnimation {
            viewModel.onAnimationFinished()
        }
    }
}

@Composable
private fun ObserveNavigationState(navController: NavHostController, viewModel: SplashViewModel) {
    val toNavigate by viewModel.isAnimationFinished
    LaunchedEffect(toNavigate) {
        if (toNavigate)
            navigateToOnboarding(navController)
    }
}

private fun navigateToOnboarding(navController: NavHostController) {
    navController.navigate(Screen.Onboarding.route) {
        popUpTo(Screen.Splash.route) {
            inclusive = true
        }
    }
}

@Composable
private fun ComposeLottieAnimation(endCallback: () -> Unit) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.opener_loading)
    )
    val logoAnimationState = animateLottieCompositionAsState(composition = composition)
    AnimateComposition(composition, logoAnimationState) {
        endCallback.invoke()
    }
}

@Composable
private fun AnimateComposition(
    composition: LottieComposition?,
    animationState: LottieAnimationState,
    endCallback: () -> Unit
) {
    LottieAnimation(
        composition = composition,
        progress = { animationState.progress },
    ).run {
        if (animationState.isAtEnd && animationState.isPlaying)
            endCallback.invoke()
    }
}
