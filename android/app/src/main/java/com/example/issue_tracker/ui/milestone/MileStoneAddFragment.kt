package com.example.issue_tracker.ui.milestone

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.issue_tracker.R
import com.example.issue_tracker.common.repeatOnLifecycleExtension
import com.example.issue_tracker.databinding.FragmentMileStoneAddBinding
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MileStoneAddFragment : Fragment() {

    private lateinit var binding: FragmentMileStoneAddBinding
    private val viewModel: MileStoneAddViewModel by viewModels()

    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText("날짜를 선택해주세요")
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_mile_stone_add, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val findNavController = findNavController()
        binding.viewModel = viewModel

        setUpDatePicker()
        setUpTextLabelError()
        setUpAddMileStoneButton(findNavController)
        observeDate()
        saveMileStone(findNavController)
    }

    private fun setUpDatePicker() {
        binding.tvMileStoneDatePicker.setOnClickListener {
            datePicker.show(parentFragmentManager, "date")
            datePicker.addOnPositiveButtonClickListener { date ->
                viewModel.onPickedDate(date)
            }
        }
    }

    private fun setUpTextLabelError() {
        binding.etMileStoneTitle.addTextChangedListener { text: Editable? ->
            binding.btnMileStoneSave.isEnabled = !(text == null || text.isEmpty())
        }
    }

    private fun setUpAddMileStoneButton(navController: NavController) {
        binding.ivGoBack.setOnClickListener {
            navController.navigate(R.id.action_mileStoneAddFragment_to_mileStoneFragment)
        }
    }

    private fun observeDate() {
        viewLifecycleOwner.repeatOnLifecycleExtension(Lifecycle.State.STARTED) {
            viewModel.mileStone.collect { mileStone ->
                binding.tvMileStoneDatePicker.text = mileStone.dueDate
            }
        }
    }

    private fun saveMileStone(navController: NavController) {
        binding.btnMileStoneSave.setOnClickListener {
            viewModel.saveData()
            navController.navigate(R.id.action_mileStoneAddFragment_to_mileStoneFragment)
        }
    }
}