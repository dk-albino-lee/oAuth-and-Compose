package com.movingroot.kakaonaveroauthwithcompose.ui.feature.onboarding

sealed class OnboardingUIEvent {
    object AuthWithKakao : OnboardingUIEvent()
    object AuthWithNaver: OnboardingUIEvent()
    object AuthWithGoogle: OnboardingUIEvent()
}
