package com.example.issue_tracker.ui.login

import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.issue_tracker.model.OAuthRequest
import com.example.issue_tracker.network.RetrofitObject
import javax.inject.Inject

class CustomWebViewClient @Inject constructor(
    private val context: Context,
    private val viewModel: LoginViewModel,
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?,
    ): Boolean {
        if (request?.url.toString().startsWith(RetrofitObject.BASE_URL)) {
            val authCode = request?.url.toString().split("=")[1]
            viewModel.requestGitHubLogin(context, OAuthRequest(authCode))
        }
        return super.shouldOverrideUrlLoading(view, request)
    }
}