package com.eamh.garmentreviews.ui.reviews.item

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.eamh.garmentreviews.R
import com.eamh.garmentreviews.databinding.ItemReviewBinding
import com.eamh.garmentreviews.model.Review

class ReviewsAdapter: RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    private val reviews = mutableListOf<Review>()
    val selectedReview = MutableLiveData<Review>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemReviewBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_review,
                        parent,
                        false)
        return ViewHolder(binding).apply {
            itemView.setOnClickListener {
                review?.let { selectedReview.value = it }
            }
        }
    }

    override fun getItemCount(): Int =
            reviews.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    fun setAllReviews(reviews: List<Review>){
        this.reviews.clear()
        this.reviews.addAll(reviews)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemReviewBinding): RecyclerView.ViewHolder(binding.root){

        var review: Review? = null

        fun bind(review: Review){
            this.review = review
            binding.viewModel =  ReviewItemViewModel(review)
            binding.executePendingBindings()
        }
    }

}