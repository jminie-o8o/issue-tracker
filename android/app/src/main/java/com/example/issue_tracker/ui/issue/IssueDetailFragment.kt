package com.example.issue_tracker.ui.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.issue_tracker.R
import com.example.issue_tracker.databinding.FragmentIssueDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class IssueDetailFragment @Inject constructor() : Fragment() {

    private lateinit var binding: FragmentIssueDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_issue_detail, container, false)
        return binding.root
    }
}