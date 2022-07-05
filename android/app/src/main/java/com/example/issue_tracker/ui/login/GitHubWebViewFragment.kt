package com.example.issue_tracker.ui.login

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentGitHubWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GitHubWebViewFragment : Fragment() {

    lateinit var binding: FragmentGitHubWebViewBinding
    private lateinit var navigator: NavController
    private val viewModel: LoginViewModel by activityViewModels()

    private val loginUrl by lazy {
        Uri.Builder()
            .scheme("https")
            .authority("github.com")
            .appendPath("login")
            .appendPath("oauth")
            .appendPath("authorize")
            .appendQueryParameter("client_id", getString(R.string.git_hub_id))
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
            webViewClient = CustomWebViewClient(context = context, viewModel = viewModel)
            loadUrl(loginUrl.toString())
        }
    }
}