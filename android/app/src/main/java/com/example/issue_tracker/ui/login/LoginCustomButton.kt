package com.example.issue_tracker.ui.login

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.CustomButtonBinding

class LoginCustomButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding: CustomButtonBinding

    init {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.custom_button,
            this,
            true
        )
        getAttrs(attrs)
    }

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoginCustomButton)
        typedArray.getString(R.styleable.LoginCustomButton_text)?.let { setButtonText(it) }
        typedArray.recycle()
    }

    private fun setButtonText(text: String) {
        binding.tvButtonTitle.text = text
    }

    fun setButtonImage(@DrawableRes resourceID: Int) {
        binding.ivBtnImage.setImageResource(resourceID)
    }
}