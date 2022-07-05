package com.example.issue_tracker.ui.label

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.issue_tracker.R
import com.example.issue_tracker.common.repeatOnLifecycleExtension
import com.example.issue_tracker.databinding.FragmentLabelAddBinding
import com.example.issue_tracker.network.CEHModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LabelAddFragment : Fragment() {

    private lateinit var binding: FragmentLabelAddBinding
    private val viewModel: LabelAddViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_label_add, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        val findNavController = findNavController()
        setupObservers()
        setUpTextLabelError()
        setClickListener(findNavController)
    }

    private fun setupObservers() {
        with(viewLifecycleOwner) {
            repeatOnLifecycleExtension {
                viewModel.label.collect {
                    binding.label = it
                }
            }
            repeatOnLifecycleExtension {
                viewModel.labelTitle.collect {
                    binding.customLabel.setLabelTitle(it)
                }
            }
            repeatOnLifecycleExtension {
                viewModel.error.collect {
                    showToast(it)
                }
            }
        }
    }

    private fun setClickListener(findNavController: NavController) {
        with(binding) {
            btnLabelSave.setOnClickListener { view ->
                viewModel?.saveLabel()
                view.isEnabled = false
            }
            ivGoBack.setOnClickListener {
                findNavController.navigate(R.id.action_labelAddFragment_to_labelFragment)
            }
        }
    }

    private fun setUpTextLabelError() {
        binding.etLabelTitle.addTextChangedListener { text: Editable? ->
            binding.btnLabelSave.isEnabled = !(text == null || text.isEmpty())
        }
    }

    private fun showToast(error: CEHModel) {
        if (error.errorMessage == CEHModel.INITIAL_MESSAGE) {
            Toast.makeText(requireContext(), getString(R.string.save_label_complete), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), error.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}