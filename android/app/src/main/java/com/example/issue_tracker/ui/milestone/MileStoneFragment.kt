package com.example.issue_tracker.ui.milestone

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
import com.example.issue_tracker.databinding.FragmentMileStoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MileStoneFragment : Fragment() {

    private lateinit var binding: FragmentMileStoneBinding
    private val viewModel: MileStoneViewModel by viewModels()
    private val adapter = MileStoneAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_mile_stone, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMileStone.adapter = adapter

        val findNavController = findNavController()
        setUpAddMileStoneButton(findNavController)
        observeMileStoneList()
    }

    private fun setUpAddMileStoneButton(navController: NavController) {
        binding.ibAddNewMileStone.setOnClickListener {
            navController.navigate(R.id.action_mileStoneFragment_to_mileStoneAddFragment)
        }
    }

    private fun observeMileStoneList() {
        viewLifecycleOwner.repeatOnLifecycleExtension {
            viewModel.mileStoneList.collect { mileStoneList ->
                adapter.submitList(mileStoneList)
            }
        }
    }
}