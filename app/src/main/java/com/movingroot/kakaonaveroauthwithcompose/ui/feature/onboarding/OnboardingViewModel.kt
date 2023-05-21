package com.movingroot.kakaonaveroauthwithcompose.ui.feature.onboarding

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.navercorp.nid.oauth.OAuthLoginCallback

class OnboardingViewModel : ViewModel() {
    private val _kakaoClicked = mutableStateOf(false)
    val kakaoClicked: State<Boolean> get() = _kakaoClicked
    private val _naverClicked = mutableStateOf(false)
    val naverClicked: State<Boolean> get() = _naverClicked
    private val _googleClicked = mutableStateOf(false)
    val googleClicked: State<Boolean> get() = _googleClicked

    val naverAuthCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            // TODO
        }

        override fun onFailure(httpStatus: Int, message: String) {
            // TODO
        }

        override fun onError(errorCode: Int, message: String) {
            // TODO
        }
    }

    fun onEvent(eventType: OnboardingUIEvent) {
        initialiseEvents()
        when (eventType) {
            is OnboardingUIEvent.AuthWithKakao -> _kakaoClicked.value = true
            is OnboardingUIEvent.AuthWithNaver -> _naverClicked.value = true
            is OnboardingUIEvent.AuthWithGoogle -> _googleClicked.value = true
        }
    }

    private fun initialiseEvents() {
        _kakaoClicked.value = false
        _naverClicked.value = false
        _googleClicked.value = false
    }

    fun handleKakaoAuthResult(token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            // TODO : 에러 처리
            return
        }
        token?.let { oAuthToken ->
            // TODO : 발급받은 토큰 정보 이용, 처리
        }
    }
}
