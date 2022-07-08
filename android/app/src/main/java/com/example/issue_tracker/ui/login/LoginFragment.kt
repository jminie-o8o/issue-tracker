package com.example.issue_tracker.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.issue_tracker.R
import com.example.issue_tracker.common.repeatOnLifecycleExtension
import com.example.issue_tracker.databinding.FragmentLoginBinding
import com.example.issue_tracker.model.LoginRequest
import com.example.issue_tracker.ui.HomeActivity
import com.example.issue_tracker.ui.common.loginWithKakao
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private var emailFlag = false
    private var passwordFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSingIn.isEnabled = false
        binding.emailInputLayout.editText?.addTextChangedListener(emailListener)
        binding.passwordInputLayout.editText?.addTextChangedListener(passwordListener)
        val navigationControl = findNavController()
        setClicks(navigationControl)
    }

    private fun setClicks(navigationControl: NavController) {
        with(binding) {
            btnToSignUp.setOnClickListener {
                navigationControl.navigate(R.id.action_loginFragment_to_signUpFragment)
            }
            btnSingIn.setOnClickListener {
                viewModel.requestLogin(
                    LoginRequest(
                        binding.emailInputEdittext.text?.toString(),
                        binding.passwordInputEditText.text?.toString()
                    )
                )
                viewLifecycleOwner.repeatOnLifecycleExtension {
                    viewModel.accessToken.collect { accessToken ->
                        if (!accessToken.isNullOrEmpty()) {
                            val intent = Intent(requireContext(), HomeActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
//            cbKakaoLogin.setOnClickListener {
//                lifecycleScope.launch {
//                    try {
//                        val oAuthToken = UserApiClient.loginWithKakao(requireContext())
//                        Log.d("Kakao", oAuthToken.toString())
//                    } catch (error: Throwable) {
//                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
//                            Log.d("Kakao", "사용자가 명시적으로 취소")
//                        } else {
//                            Log.e("Kakao", "인증 에러 발생", error)
//                        }
//                    }
//                }
//            }
        }
        binding.cbGithubLogin.setOnClickListener {
            navigationControl.navigate(R.id.action_loginFragment_to_gitHubWebViewFragment)
        }
    }

    private val emailListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.emailInputLayout.error = "이메일을 입력해주세요."
                        emailFlag = false
                    }
                    else -> {
                        binding.emailInputLayout.error = null
                        emailFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val passwordListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.passwordInputLayout.error = "이메일을 입력해주세요."
                        passwordFlag = false
                    }
                    else -> {
                        binding.passwordInputLayout.error = null
                        passwordFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    fun flagCheck() {
        binding.btnSingIn.isEnabled = emailFlag && passwordFlag
    }
}