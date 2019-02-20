package com.eamh.garmentreviews.ui.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eamh.garmentreviews.R
import com.eamh.garmentreviews.model.Review
import com.eamh.garmentreviews.repository.Repository
import com.eamh.garmentreviews.ui.reviews.item.ReviewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ReviewsFragmentViewModel: ViewModel(), KoinComponent {

    private val repository: Repository by inject()
    val reviewsAdapter: ReviewsAdapter = ReviewsAdapter()
    private val disposables = CompositeDisposable()

    val showInfoToUser = MutableLiveData<Int>()
    val selectedReview: LiveData<Review> = reviewsAdapter.selectedReview
    val showLoading = MutableLiveData<Boolean>()

    init {
        loadReviews()
    }

    private fun onRetrieveReviewsSuccess(reviews: List<Review>){
        showLoading(false)
        reviewsAdapter.setAllReviews(reviews)
    }

    private fun onRetrieveReviewsError(throwable: Throwable){
        showLoading(false)
        showInfoToUser.value = R.string.retrieve_reviews_error
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun loadReviews() {
        showLoading(true)
        repository.getReviews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onRetrieveReviewsSuccess,::onRetrieveReviewsError)
                .also { disposables.add(it) }
    }

    private fun showLoading(show: Boolean){
        showLoading.value = show
    }
}