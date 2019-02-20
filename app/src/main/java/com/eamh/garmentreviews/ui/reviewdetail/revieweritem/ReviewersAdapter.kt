package com.eamh.garmentreviews.ui.reviewdetail.revieweritem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.eamh.garmentreviews.R
import com.eamh.garmentreviews.databinding.ItemReviewerBinding
import com.eamh.garmentreviews.model.Reviewer

class ReviewersAdapter: RecyclerView.Adapter<ReviewersAdapter.ViewHolder>() {

    private val reviewers = mutableListOf<Reviewer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemReviewerBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_reviewer,
                        parent,
                        false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int =
            reviewers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewers[position])
    }

    fun setAllReviewers(reviewers: List<Reviewer>){
        this.reviewers.clear()
        this.reviewers.addAll(reviewers)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemReviewerBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(reviewer: Reviewer){
            binding.viewModel = ReviewerItemViewModel(reviewer)
            binding.executePendingBindings()
        }
    }

}