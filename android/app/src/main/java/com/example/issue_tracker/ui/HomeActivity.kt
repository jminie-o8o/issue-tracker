package com.example.issue_tracker.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        val accessToken = Intent().extras?.getString("accessToken")
        if (accessToken != null) {
            Log.d("HomeActivity", accessToken)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.issueBottomNavigation.setupWithNavController(findNavController(R.id.nav_home_fragment))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val imm: InputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }
}