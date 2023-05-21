package com.movingroot.kakaonaveroauthwithcompose.ui.feature.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SplashViewModel : ViewModel() {
    private val _isAnimationFinished = mutableStateOf(false)
    val isAnimationFinished: State<Boolean> = _isAnimationFinished

    fun onAnimationFinished() {
        _isAnimationFinished.value = true
    }
}
