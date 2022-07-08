package com.example.issue_tracker.ui.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.issue_tracker.R
import com.example.issue_tracker.common.repeatOnLifecycleExtension
import com.example.issue_tracker.databinding.FragmentIssueAddBinding
import com.example.issue_tracker.model.IssueAddRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class IssueAddFragment : Fragment() {

    private lateinit var binding: FragmentIssueAddBinding
    private val viewModel: IssueAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_issue_add, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val findNavController = findNavController()
        viewModel.loadLabelAndMileStone()
        observeMenuButtons()
        observeClickedMenuText(findNavController)
        goBackIssue(findNavController)
        addIssue()
    }

    private fun observeMenuButtons() {
        with(binding) {
            ibFilterButtonLabel.setOnClickListener {
                val labelList = viewModel.labelList.value
                val labelPopupMenu =
                    PopupMenu(requireContext(), binding.ibFilterButtonLabel).apply {
                        labelList.forEachIndexed { index, item ->
                            menu.add(Menu.NONE, index, index, item.labelTitle)
                        }
                    }
                labelPopupMenu.setOnMenuItemClickListener { item ->
                    viewModel.findClickedLabelMenu(item.itemId)
                    false
                }
                labelPopupMenu.show()
            }
            ibFilterButtonIssueMileStone.setOnClickListener {
                val mileStoneList = viewModel.mileStoneList.value
                val mileStonePopupMenu =
                    PopupMenu(requireContext(), binding.ibFilterButtonIssueMileStone).apply {
                        mileStoneList.forEachIndexed { index, item ->
                            menu.add(Menu.NONE, index, index, item.title)
                        }
                    }
                mileStonePopupMenu.setOnMenuItemClickListener { item ->
                    viewModel.findClickedMileStoneMenu(item.itemId)
                    false
                }
                mileStonePopupMenu.show()
            }
        }
    }

    private fun observeClickedMenuText(findNavController: NavController) {
        with(viewLifecycleOwner) {
            repeatOnLifecycleExtension {
                viewModel.labelChoose.collect {
                    binding.label = it
                }
            }
            repeatOnLifecycleExtension {
                viewModel.mileStoneChoose.collect {
                    binding.mileStone = it
                }
            }
            repeatOnLifecycleExtension {
                viewModel.isSuccess.collect {
                    if (it) {
                        findNavController.navigate(R.id.action_issueAddFragment_to_issueFragment)
                    } else {
                        Toast.makeText(context, "추가가 되지 않았습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun goBackIssue(findNavController: NavController) {
        binding.ivGoBack.setOnClickListener {
            findNavController.navigate(R.id.action_issueAddFragment_to_issueFragment)
        }
    }

    private fun addIssue() {
        binding.btnIssueSave.setOnClickListener {
            viewModel.addIssue(
                IssueAddRequest(
                    viewModel.labelChoose.value.labelId,
                    viewModel.mileStoneChoose.value.mileStoneId,
                    IssueAddRequest.INITIAL_AUTHOR_ID,
                    IssueAddRequest.INITIAL_ASSIGNEE_ID,
                    binding.etIssueTitle.text.toString(),
                    binding.etIssueContent.text.toString(),
                    IssueAddRequest.INITIAL_IS_OPENED
                )
            )
        }
    }
}