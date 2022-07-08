package com.example.issue_tracker.ui.login

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_git_hub_web_view, container, false)
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
        setupObservers()
    }

    private fun setupObservers() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.accessToken.collect { accessToken ->
                if (!accessToken.isNullOrEmpty()) {
                    val intent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    inner class CustomWebViewClient : WebViewClient() {

        private fun checkUrl(request: WebResourceRequest?) {
            val url = request?.url ?: return
            val code = url.getQueryParameter(GITHUB_OAUTH_CODE_PARAM_KEY)

            if (url.scheme == GITHUB_OAUTH_REDIRECTION_SCHEME && url.host == GITHUB_OAUTH_REDIRECTION_HOST && code != null) {
                viewModel.requestGitHubLogin(GitHubOAuthRequest(code))
            }
        }

        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            checkUrl(request)
            return true // shouldOverrideUrlLoading 함수 반환 값을 return true 로 하면 다음 page 를 load 하지 않는다.
        }
    }

    companion object {
        private const val GITHUB_OAUTH_REDIRECTION_SCHEME = "http"
        private const val GITHUB_OAUTH_REDIRECTION_HOST = "3.34.97.60"
        private const val GITHUB_OAUTH_CODE_PARAM_KEY = "code"
    }
}