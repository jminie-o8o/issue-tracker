package com.example.issue_tracker.ui.common

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun setVisible(view: View, isClicked: Boolean?) {
    view.visibility = if (isClicked == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}