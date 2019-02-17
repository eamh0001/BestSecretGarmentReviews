package com.eamh.garmentreviews.ui.reviewdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eamh.garmentreviews.R
import com.eamh.garmentreviews.model.Review
import com.eamh.garmentreviews.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ReviewDetailViewModel(private val reviewId: Int): ViewModel(), KoinComponent {

    private val repository: Repository by inject()
    private val disposables = CompositeDisposable()

    val reviewersAdapter = ReviewersAdapter()
    val review = MutableLiveData<Review>()
    val showLoading = MutableLiveData<Boolean>()
    val showInfoToUser = MutableLiveData<Int>()

    init {
        loadReview()
    }

    fun loadReview(){
        showLoading(true)
        repository.getReview(reviewId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(::onRetrieveReviewSuccess, ::onRetrieveReviewError)
                .also { disposables.add(it) }
    }

    private fun onRetrieveReviewSuccess(review: Review) {
        this.review.value = review
        reviewersAdapter.setAllReviewers(review.reviewers)
        showLoading(false)
    }

    private fun onRetrieveReviewError(throwable: Throwable) {
        showLoading(false)
        showInfoToUser.value = R.string.retrieve_reviewers_error
    }

    private fun showLoading(show: Boolean){
        showLoading.value = show
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}