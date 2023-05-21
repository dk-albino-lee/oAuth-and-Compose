package com.movingroot.kakaonaveroauthwithcompose.ui.feature.onboarding

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.movingroot.kakaonaveroauthwithcompose.MainActivity
import com.movingroot.kakaonaveroauthwithcompose.R
import com.movingroot.kakaonaveroauthwithcompose.ui.theme.KakaoNaverOAuthWithComposeTheme
import com.navercorp.nid.NaverIdLoginSDK

@Composable
fun OnboardingScreen(
    navController: NavHostController,
    viewModel: OnboardingViewModel = viewModel()
) {
    val context = LocalContext.current
    setSignInAuths(context, viewModel)
    
    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            LogoAndButtons(viewModel = viewModel)
        }
    }
}

private fun setSignInAuths(context: Context, viewModel: OnboardingViewModel) {
    val kakaoAuth by viewModel.kakaoClicked
    val naverAuth by viewModel.naverClicked
    val googleAuth by viewModel.googleClicked
    trySignIn(kakaoAuth, naverAuth, googleAuth, context, viewModel)
}

private fun trySignIn(
    kakao: Boolean,
    naver: Boolean,
    google: Boolean,
    context: Context,
    viewModel: OnboardingViewModel
) {
    if (kakao)
        signInWithKakao(context, viewModel)
    if (naver)
        signInWithNaver(context, viewModel)
    if (google)
        signInWithGoogle(context)
}

@Composable
private fun LogoAndButtons(viewModel: OnboardingViewModel) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .padding(bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo(scope = this)
        ButtonsField(scope = this, viewModel = viewModel)
    }
}

@Composable
private fun Logo(scope: ColumnScope) {
    scope.run {
        Box(modifier = Modifier.weight(1f)) {
            SetLogoAnimation()
        }
    }
}

@Composable
private fun SetLogoAnimation() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.sleeping_koala)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
    )
}

@Composable
private fun ButtonsField(scope: ColumnScope, viewModel: OnboardingViewModel) {
    scope.run {
        KakaoOAuth(viewModel)
        Spacer(modifier = Modifier.height(15.dp))
        NaverOAuth(viewModel)
        Spacer(modifier = Modifier.height(15.dp))
        GoogleOAuth(viewModel)
    }
}

@Composable
private fun KakaoOAuth(viewModel: OnboardingViewModel) {
    SignInButton(
        backgroundColor = Color.Yellow,
        text = "Kakao"
    ) {
        viewModel.onEvent(OnboardingUIEvent.AuthWithKakao)
    }
}

@Composable
private fun NaverOAuth(viewModel: OnboardingViewModel) {
    SignInButton(
        backgroundColor = Color.Green,
        text = "Naver"
    ) {
        viewModel.onEvent(OnboardingUIEvent.AuthWithNaver)
    }
}

@Composable
private fun GoogleOAuth(viewModel: OnboardingViewModel) {
    SignInButton(
        backgroundColor = Color.LightGray,
        text = "Google"
    ) {
        viewModel.onEvent(OnboardingUIEvent.AuthWithGoogle)
    }
}

@Composable
private fun SignInButton(
    backgroundColor: Color,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.setButtonModifier(backgroundColor, onClick)
    ) {
        Text(
            text = text,
            color = Color.Black,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 15.dp)
        )
    }
}

private fun Modifier.setButtonModifier(
    backgroundColor: Color,
    onClick: () -> Unit
): Modifier {
    return this.then(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(color = backgroundColor)
            .clickable { onClick.invoke() }
    )
}

private fun signInWithNaver(context: Context, viewModel: OnboardingViewModel) {
    NaverIdLoginSDK.authenticate(
        context = context,
        callback = viewModel.naverAuthCallback
    )
}

private fun signInWithKakao(context: Context, viewModel: OnboardingViewModel) {
    UserApiClient.instance.run {
        if (isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoAccount(context) { token, error ->
                viewModel.handleKakaoAuthResult(token, error)
            }
        } else {
            // TODO : 카카오톡 설치 안 되어 카카오 계정으로 로그인 할 경우 사용
            //  위와 동일한 콜백 사용시 안 되는 것이 있는지 체크 필요
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                viewModel.handleKakaoAuthResult(token, error)
            }
            loginWithKakaoAccount(context, callback = callback)
        }
    }
}

private fun signInWithGoogle(context: Context) {
    (findActivity(context) as MainActivity).getGoogleAuth()
}

private fun findActivity(_context: Context): Activity {
    var context = _context
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    KakaoNaverOAuthWithComposeTheme {
        OnboardingScreen(navController = rememberNavController())
    }
}
