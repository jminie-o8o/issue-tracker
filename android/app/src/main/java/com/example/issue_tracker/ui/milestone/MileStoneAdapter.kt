package com.example.issue_tracker.ui.milestone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemMileStoneRecyclerViewBinding
import com.example.issue_tracker.model.MileStone

class MileStoneAdapter :
    ListAdapter<MileStone, MileStoneAdapter.MileStoneViewHolder>(MileStoneDiffCallback) {

    class MileStoneViewHolder(private val binding: ItemMileStoneRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mileStone: MileStone) {
            binding.mileStone = mileStone
            if (mileStone.description == "") {
                binding.tvMileStoneRecyclerDescription.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MileStoneViewHolder {
        val binding =
            ItemMileStoneRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent,
                false)
        return MileStoneViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MileStoneViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        object MileStoneDiffCallback : DiffUtil.ItemCallback<MileStone>() {
            override fun areItemsTheSame(oldItem: MileStone, newItem: MileStone): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: MileStone, newItem: MileStone): Boolean {
                return oldItem == newItem
            }
        }
    }
}