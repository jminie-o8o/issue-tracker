package com.example.issue_tracker.ui.label

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.issue_tracker.R
import com.example.issue_tracker.common.repeatOnLifecycleExtension
import com.example.issue_tracker.databinding.FragmentLabelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LabelFragment : Fragment() {

    private lateinit var binding: FragmentLabelBinding
    private val viewModel: LabelViewModel by viewModels()
    private val adapter = LabelListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_label, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val findNavController = findNavController()
        binding.rvLabelList.adapter = adapter

        setRecyclerViewAdapter()
        setClickListener(findNavController)
    }

    private fun setRecyclerViewAdapter() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.labelList.collect { labelList ->
                adapter.submitList(labelList)
            }
        }
    }

    private fun setClickListener(findNavController: NavController) {
        binding.ibAddNewLabel.setOnClickListener {
            findNavController.navigate(R.id.action_labelFragment_to_labelAddFragment)
        }
    }
}