package com.eamh.garmentreviews.ui.reviews.item

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eamh.garmentreviews.R
import com.eamh.garmentreviews.databinding.ItemReviewDataBinding
import com.eamh.garmentreviews.databinding.ItemReviewHeaderBinding
import com.eamh.garmentreviews.model.Review

class GroupedReviewsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val groupedReviews = mutableListOf<ListItem>()

    val selectedReview = MutableLiveData<Review>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ListItem.Type.DATA.ordinal -> createDataViewHolder(parent)
            ListItem.Type.HEADER.ordinal -> createHeaderViewHolder(parent)
            else -> throw Exception("View type doesn't found $viewType")
        }
    }

    override fun getItemViewType(position: Int) =
            groupedReviews[position].getType().ordinal

    override fun getItemCount(): Int =
            groupedReviews.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            ListItem.Type.DATA.ordinal -> (holder as DataViewHolder).bind(groupedReviews[position] as DataListItem)
            ListItem.Type.HEADER.ordinal -> (holder as HeaderViewHolder).bind(groupedReviews[position] as HeaderListItem)
        }
    }

    fun setAllReviews(reviews: List<Review>){
        groupedReviews.clear()
        groupedReviews.addAll(ReviewsGrouper(reviews).getGrouped(ReviewsGrouper.By.DATE))
        notifyDataSetChanged()
    }

    private fun createDataViewHolder(parent: ViewGroup): RecyclerView.ViewHolder{
        val binding: ItemReviewDataBinding=
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_review_data,
                        parent,
                        false)
        return DataViewHolder(binding).apply {
            itemView.setOnClickListener {
                review?.let { selectedReview.value = it }
            }
        }
    }

    private fun createHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder{
        val binding: ItemReviewHeaderBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_review_header,
                        parent,
                        false)
        return HeaderViewHolder(binding)
    }
}

class DataViewHolder(private val binding: ItemReviewDataBinding): RecyclerView.ViewHolder(binding.root){

    var review: Review? = null

    fun bind(dataListItem: DataListItem){
        this.review = dataListItem.review
        binding.viewModel =  ReviewItemViewModel(dataListItem.review)
        binding.executePendingBindings()
    }
}

class HeaderViewHolder(private val binding: ItemReviewHeaderBinding): RecyclerView.ViewHolder(binding.root){

    init {
        itemView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                val layoutParams = itemView.layoutParams
                if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                    layoutParams.isFullSpan = true
                }
                itemView.layoutParams = layoutParams
                val layoutManager = (itemView.parent as RecyclerView).layoutManager as StaggeredGridLayoutManager
                layoutManager.invalidateSpanAssignments()
                itemView.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })
    }

    fun bind(headerListItem: HeaderListItem){
        binding.title =  headerListItem.text
        binding.executePendingBindings()
    }
}
