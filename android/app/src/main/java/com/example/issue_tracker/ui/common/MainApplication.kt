package com.example.issue_tracker.ui.common

import android.app.Application
import com.example.issue_tracker.R
import com.example.issue_tracker.common.PreferenceUtil
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
        prefs = PreferenceUtil(applicationContext)
    }
}