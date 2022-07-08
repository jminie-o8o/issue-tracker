package com.example.issue_tracker.ui.label

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.issue_tracker.R
import com.example.issue_tracker.common.repeatOnLifecycleExtension
import com.example.issue_tracker.databinding.FragmentLabelBinding
import com.example.issue_tracker.ui.common.SwipeHelperCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LabelFragment : Fragment(), ActionMode.Callback {

    private lateinit var binding: FragmentLabelBinding
    private val viewModel: LabelViewModel by viewModels()

    private val adapter: LabelListAdapter by lazy {
        LabelListAdapter(
            startActionMode = { view -> startMyActionMode(view) },
            changeLongClickState = { viewModel.changeClickedState() },
            stopActionMode = { stopMyActionMode() }
        )
    }

    private val swipeHelperCallback: SwipeHelperCallback by lazy {
        SwipeHelperCallback(
            getIssueSwiped = { item -> viewModel.getLabelSwiped(item) },
            changeIssueSwiped = { item, isSwiped -> viewModel.changeLabelSwiped(item, isSwiped) },
            clampView = R.id.cv_label_swipe_view
        ).apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)
        }
    }

    private var actionMode: ActionMode? = null

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

        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.rvLabelList)
        setRecyclerViewAdapter()
        removeClamp()
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

    private fun removeClamp() {
        binding.rvLabelList.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
                swipeHelperCallback.removePreviousClamp(binding.rvLabelList)
                v.performClick()
            }
            false
        }
    }

    private fun startMyActionMode(view: View) {
        actionMode = view.startActionMode(this)
    }

    private fun stopMyActionMode() {
        actionMode?.finish()
    }

    override fun onCreateActionMode(mode: ActionMode, menu: Menu?): Boolean {
        val inflater: MenuInflater = mode.menuInflater
        inflater.inflate(R.menu.label_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
    }
}