package com.example.issue_tracker.ui.milestone

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
import com.example.issue_tracker.databinding.FragmentMileStoneBinding
import com.example.issue_tracker.ui.common.SwipeHelperCallback
import com.example.issue_tracker.ui.label.LabelListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MileStoneFragment : Fragment(), ActionMode.Callback {

    private lateinit var binding: FragmentMileStoneBinding
    private val viewModel: MileStoneViewModel by viewModels()

    private val adapter: MileStoneAdapter by lazy {
        MileStoneAdapter(
            startActionMode = { view -> startMyActionMode(view) },
            changeLongClickState = { viewModel.changeClickedState() },
            stopActionMode = { stopMyActionMode() }
        )
    }

    private val swipeHelperCallback: SwipeHelperCallback by lazy {
        SwipeHelperCallback(
            getIssueSwiped = { item -> viewModel.getLabelSwiped(item) },
            changeIssueSwiped = { item, isSwiped -> viewModel.changeLabelSwiped(item, isSwiped) },
            clampView = R.id.cv_milestone_swipe_view
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
            DataBindingUtil.inflate(inflater, R.layout.fragment_mile_stone, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMileStone.adapter = adapter

        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.rvMileStone)
        val findNavController = findNavController()
        setUpAddMileStoneButton(findNavController)
        observeMileStoneList()
        removeClamp()
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

    private fun removeClamp() {
        binding.rvMileStone.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
                swipeHelperCallback.removePreviousClamp(binding.rvMileStone)
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