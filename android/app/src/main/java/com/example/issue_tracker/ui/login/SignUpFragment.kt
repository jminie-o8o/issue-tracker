package com.example.issue_tracker.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.issue_tracker.R
import com.example.issue_tracker.common.repeatOnLifecycleExtension
import com.example.issue_tracker.databinding.FragmentSignUpBinding
import com.example.issue_tracker.model.SignUpRequest
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    private var idFlag = false
    private var passwordFlag = false
    private var passwordCheckFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val findNavController = findNavController()
        binding.signUpButton.isEnabled = false
        binding.idTextInputLayout.editText?.addTextChangedListener(idListener)
        binding.passwordTextInputLayout.editText?.addTextChangedListener(passwordListener)
        binding.passwordRecheckTextInputLayout.editText?.addTextChangedListener(
            passwordRecheckListener
        )
        binding.signUpButton.setOnClickListener {
            requestSignUp()
        }
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.signUpResponse.collect {
                if (it.statusCode == 200) {
                    Toast.makeText(requireContext(), "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    goToLoginFragment(findNavController)
                } else {
                    Toast.makeText(requireContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun requestSignUp() {
        val request = SignUpRequest(
            binding.idTextInputEditText.text?.toString(),
            binding.passwordRecheckTextInputEditText.text?.toString(),
            binding.nickNameTextInputEditText.text?.toString()
        )
        viewModel.requestSignUp(request)
    }

    private fun goToLoginFragment(findNavController: NavController) {
        findNavController.navigate(R.id.action_signUpFragment_to_loginFragment)
    }

    private val idListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.idTextInputLayout.error = "아이디를 입력해주세요."
                        idFlag = false
                    }
                    !checkEmailRegex(s.toString()) -> {
                        binding.idTextInputLayout.error = "이메일 아이디 양식이 맞지 않습니다"
                        idFlag = false
                    }
                    else -> {
                        binding.idTextInputLayout.error = null
                        idFlag = true
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
                        binding.passwordTextInputLayout.error = "비밀번호를 입력해주세요."
                        passwordFlag = false
                    }
                    s.isNotEmpty() -> {
                        binding.passwordTextInputLayout.error = null
                        passwordFlag = true
                        when {
                            binding.passwordRecheckTextInputLayout.editText?.text.toString() != ""
                                    && binding.passwordRecheckTextInputLayout.editText?.text.toString() != binding.passwordTextInputLayout.editText?.text.toString() -> {
                                binding.passwordRecheckTextInputLayout.error = "비밀번호가 일치하지 않습니다"
                                passwordCheckFlag = false
                                passwordFlag = true
                            }
                            binding.passwordRecheckTextInputLayout.editText?.text.toString() == "" -> {
                                passwordCheckFlag = false
                            }
                            else -> {
                                binding.passwordRecheckTextInputLayout.error = null
                                passwordCheckFlag = true
                            }
                        }
                    }
                }
                flagCheck()
            }
        }
    }

    private val passwordRecheckListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        passwordCheckFlag = false
                    }
                    binding.passwordRecheckTextInputLayout.editText?.text.toString() != binding.passwordTextInputLayout.editText?.text.toString() -> {
                        binding.passwordRecheckTextInputLayout.error = "비밀번호가 일치하지 않습니다"
                        passwordCheckFlag = false
                    }
                    else -> {
                        binding.passwordRecheckTextInputLayout.error = null
                        passwordCheckFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private fun checkEmailRegex(id: String): Boolean {
        return emailValidation.matches(id)
    }

    fun flagCheck() {
        binding.signUpButton.isEnabled = idFlag && passwordFlag && passwordCheckFlag
    }

    companion object {
        private val emailValidation =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex()
    }
}