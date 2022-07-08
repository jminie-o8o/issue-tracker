package com.example.issue_tracker.ui.issue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemIssueSearchRecyclerViewBinding
import com.example.issue_tracker.model.Issue

class IssueSearchAdapter : ListAdapter<Issue, IssueSearchAdapter.IssueSearchViewHolder>(IssueAddDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueSearchViewHolder {
        val binding = ItemIssueSearchRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueSearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class IssueSearchViewHolder(private val binding: ItemIssueSearchRecyclerViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(issue: Issue) {
            binding.issue = issue
        }
    }
}

object IssueAddDiffCallback : DiffUtil.ItemCallback<Issue>() {
    override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem.issueId == newItem.issueId
    }

    override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem == newItem
    }
}