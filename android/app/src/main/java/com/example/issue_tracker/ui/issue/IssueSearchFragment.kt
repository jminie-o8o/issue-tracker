package com.example.issue_tracker.ui.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.issue_tracker.R
import com.example.issue_tracker.common.repeatOnLifecycleExtension
import com.example.issue_tracker.databinding.FragmentIssueSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class IssueSearchFragment : Fragment() {

    lateinit var binding: FragmentIssueSearchBinding
    lateinit var adapter: IssueSearchAdapter
    private val viewModel: IssueViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_issue_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = IssueSearchAdapter()
        binding.rvIssueSearch.adapter = adapter
        listenSearchWordChange()
        updateSearchWord()
        updateAdapter()
        val findNavController = findNavController()
        goBackIssue(findNavController)
    }

    private fun listenSearchWordChange() {
        binding.etSearchIssue.addTextChangedListener { text ->
            if (text != null) viewModel.handleSearchWord(text.toString())
        }
    }

    private fun updateSearchWord() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.searchWord.collect {
                viewModel.getSearchIssue(it)
            }
        }
    }

    private fun updateAdapter() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.searchIssueList.collect {
                adapter.submitList(it)
            }
        }
    }

    private fun goBackIssue(findNavController: NavController) {
        binding.btnCloseIssueSearch.setOnClickListener {
            findNavController.navigate(R.id.action_issueSearchFragment_to_issueFragment)
        }
    }
}