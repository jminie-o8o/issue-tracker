package com.example.issue_tracker.ui.issue

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.issue_tracker.R
import com.example.issue_tracker.common.repeatOnLifecycleExtension
import com.example.issue_tracker.databinding.FragmentIssueBinding
import com.example.issue_tracker.ui.common.SwipeHelperCallback

class IssueFragment : Fragment() {

    lateinit var binding: FragmentIssueBinding
    lateinit var adapter: IssueAdapter
    private val viewModel: IssueViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_issue, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val findNavController = findNavController()
        goToIssueFilterFragments(findNavController)
        goToSearchIssueFragment(findNavController)
        goToIssueAddFragment(findNavController)
        changeActionBarState()

        adapter = IssueAdapter(
            closeIssue = { issueId -> viewModel.closeIssue(issueId) },
            changeClickState = { viewModel.checkLongClicked },
            addCheckIssue = { issueId -> viewModel.addChecked(issueId) },
            removeCheckedIssue = { issueId -> viewModel.removeChecked(issueId) }
        )

        val swipeHelperCallback = SwipeHelperCallback(
            getIssueSwiped = { index -> viewModel.getIssueSwiped(index) },
            changeIssueSwiped = { index, isClamped ->
                viewModel.changeIssueSwiped(index,
                    isClamped)
            }
        ).apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)
        }
        binding.rvIssue.adapter = adapter
        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.rvIssue)

        binding.rvIssue.setOnTouchListener { _, _ ->
            swipeHelperCallback.removePreviousClamp(binding.rvIssue)
            false
        }

        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.issueList.collect {
                adapter.submitList(it)
            }
        }

        binding.btnCloseLongClick.setOnClickListener {
            viewModel.clearCheckedIdList()
            viewModel.changeClickedState()
        }

        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.closeIssue.collect {
                Toast.makeText(requireContext(), "$it 번 이슈가 닫혔습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAppBarCloseIssue.setOnClickListener {
            viewModel.closeIssueByCheckBox(viewModel.checkedIssueIdList.value)
            Toast.makeText(requireContext(),
                "${viewModel.checkedIssueIdListTemp.value} 번 이슈가 닫혔습니다.",
                Toast.LENGTH_SHORT).show()
            viewModel.clearCheckedIdList()
            viewModel.changeClickedState()
        }

        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.error.collect {
                if (it.throwable != null) {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToIssueFilterFragments(findNavController: NavController) {
        binding.btnIssueFilter.setOnClickListener {
            findNavController.navigate(R.id.action_issueFragment_to_issueFilterFragment)
        }
    }

    private fun goToIssueAddFragment(findNavController: NavController) {
        binding.fabIssueAdd.setOnClickListener {
            findNavController.navigate(R.id.action_issueFragment_to_issueAddFragment)
        }
    }

    private fun goToSearchIssueFragment(findNavController: NavController) {
        binding.btnIssueSearch.setOnClickListener {
            findNavController.navigate(R.id.action_issueFragment_to_issueSearchFragment)
        }
    }

    private fun changeActionBarState() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.checkLongClicked.collect {
                binding.isClicked = it
            }
        }
    }
}