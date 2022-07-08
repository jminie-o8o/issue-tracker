package com.example.issue_tracker.ui.label

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.CustomLabelBinding

class CustomLabel(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private var binding: CustomLabelBinding

    init {
        binding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_label, this, true)
    }

    fun setLabelTitle(text: String?) {
        binding.tvCustomLabelTitle.text = text
    }

    fun setLabelOpenCount(count: Int) {
        val formattedString = String.format(context.getString(R.string.open_issue_count), count)
        binding.tvCustomLabelTitle.text = formattedString
    }

    fun setLabelCloseCount(count: Int) {
        val formattedString = String.format(context.getString(R.string.closed_issue_count), count)
        binding.tvCustomLabelTitle.text = formattedString
    }

    fun setLabelColor(color: String) {
        val parsedColor = Color.parseColor(color)
        val mode = BlendMode.DST_OVER
        changeColor(parsedColor, mode)
    }

    fun setLabelColorResource(@ColorRes color: Int) {
        val colorF = context.getColor(color)
        val mode = BlendMode.MULTIPLY
        changeColor(colorF, mode)
    }

    fun setLabelImage(@DrawableRes drawable: Int) {
        binding.ivLabel.visibility = VISIBLE
        binding.ivLabel.setImageResource(drawable)
    }

    private fun changeColor(color: Int, mode: BlendMode) {
        val drawable = context.getDrawable(R.drawable.custom_label_background)
            ?.let { DrawableCompat.wrap(it) }
        drawable?.colorFilter = BlendModeColorFilter(color, mode)
        binding.customLabelLayout.background = drawable
    }
}
