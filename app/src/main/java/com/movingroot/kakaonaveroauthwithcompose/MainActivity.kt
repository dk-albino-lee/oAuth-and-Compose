package com.movingroot.kakaonaveroauthwithcompose

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.movingroot.kakaonaveroauthwithcompose.ui.feature.SetNavGraph
import com.movingroot.kakaonaveroauthwithcompose.ui.theme.KakaoNaverOAuthWithComposeTheme


class MainActivity : ComponentActivity() {
    private val viewModel: ActivityViewModel by viewModels()
    private val googleSignInLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    // TODO : Intent 로부터 필요 정보 수신
                    //  RESULT_OK 여도 data 가 null 로 수신돤다.
                    //  -> 이미 한 번 로그인 한 상태라서 그런건지 확인 필요
                }
            } else {
                // TODO : sign in 실패
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KakaoNaverOAuthWithComposeTheme {
                val navController = rememberNavController()
                SetNavGraph(controller = navController)
            }
        }
    }

    fun getGoogleAuth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }
}
