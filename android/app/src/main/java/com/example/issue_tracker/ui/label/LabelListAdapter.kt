package com.example.issue_tracker.ui.label

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemLabelRecyclerViewBinding
import com.example.issue_tracker.model.Label

class LabelListAdapter(
    private val startActionMode: (View) -> Unit,
    private val changeLongClickState: () -> Unit,
    private val stopActionMode: () -> Unit,
) : ListAdapter<Label, LabelListAdapter.LabelViewHolder>(LabelDiffCallback) {

    inner class LabelViewHolder(
        private val binding: ItemLabelRecyclerViewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(label: Label) {
            binding.label = label
            binding.cvLabelSwipeView.setOnLongClickListener {
                showActionMode(label.isLongClicked, it)
                changeLongClickState()
                false
            }
        }

        private fun showActionMode(isLongClicked: Boolean, view: View) {
            if (isLongClicked) {
                stopActionMode()
            } else {
                startActionMode(view)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        val binding =
            ItemLabelRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LabelViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        object LabelDiffCallback : DiffUtil.ItemCallback<Label>() {
            override fun areItemsTheSame(oldItem: Label, newItem: Label): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: Label, newItem: Label): Boolean {
                return oldItem == newItem
            }
        }
    }
}