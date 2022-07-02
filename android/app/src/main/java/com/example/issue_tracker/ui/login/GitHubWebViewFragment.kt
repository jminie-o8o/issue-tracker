package com.example.issue_tracker.ui.login

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.issue_tracker.R
import com.example.issue_tracker.common.repeatOnLifecycleExtension
import com.example.issue_tracker.databinding.FragmentGitHubWebViewBinding
import com.example.issue_tracker.model.GitHubOAuthRequest
import com.example.issue_tracker.ui.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class GitHubWebViewFragment : Fragment() {

    lateinit var binding: FragmentGitHubWebViewBinding
    private lateinit var navigator: NavController
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_git_hub_web_view, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = Navigation.findNavController(view)
        binding.githubWebView.run {
            webViewClient = CustomWebViewClient()
            loadUrl("https://github.com/login/oauth/authorize?client_id=" + getString(R.string.git_hub_id))
        }
    }

    inner class CustomWebViewClient : WebViewClient() {

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request?.url.toString().startsWith("http://3.34.136.141:8080/")) {
                val authCode = request?.url.toString().split("=")[1]
                viewModel.requestGitHubLogin(GitHubOAuthRequest(authCode))
                viewLifecycleOwner.repeatOnLifecycleExtension {
                    viewModel.accessToken.collect { accessToken ->
                        if (!accessToken.isNullOrEmpty()) {
                            Log.d("TEST2", accessToken.toString())
                            val intent = Intent(requireContext(), HomeActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
            return super.shouldOverrideUrlLoading(view, request)
        }
    }
}