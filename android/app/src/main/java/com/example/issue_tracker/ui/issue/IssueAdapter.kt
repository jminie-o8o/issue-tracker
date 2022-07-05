package com.example.issue_tracker.ui.issue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.issue_tracker.databinding.ItemIssueRecyclerViewBinding
import com.example.issue_tracker.model.Issue

class IssueAdapter(
    private val closeIssue: (Int) -> Unit,
    private val changeClickState: () -> Unit,
    private val addCheckIssue: (Int) -> Unit,
    private val removeCheckedIssue: (Int) -> Unit,
) : ListAdapter<Issue, IssueAdapter.IssueViewHolder>(IssueDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder {
        val binding =
            ItemIssueRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IssueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class IssueViewHolder(private val binding: ItemIssueRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(issue: Issue) {
            binding.issue = issue
            binding.tvCloseIssue.setOnClickListener {
                closeIssue(issue.issueId)
            }

            binding.cvSwipeView.setOnLongClickListener {
                changeClickState
                false
            }

            binding.issueCheckBox.setOnCheckedChangeListener { _, isChecked ->
                when (isChecked) {
                    true -> addCheckIssue(issue.issueId)
                    false -> removeCheckedIssue(issue.issueId)
                }
            }
        }
    }
}

object IssueDiffCallback : DiffUtil.ItemCallback<Issue>() {
    override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem.issueId == newItem.issueId
    }

    override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
        return oldItem == newItem
    }
}