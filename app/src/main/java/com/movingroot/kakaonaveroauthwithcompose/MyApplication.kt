package com.movingroot.kakaonaveroauthwithcompose

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NaverIdLoginSDK.initialize(
            context = this,
            clientId = BuildConfig.NAVER_ID,
            clientSecret = BuildConfig.NAVER_SECRET,
            clientName = BuildConfig.NAVER_NAME
        )
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }
}
