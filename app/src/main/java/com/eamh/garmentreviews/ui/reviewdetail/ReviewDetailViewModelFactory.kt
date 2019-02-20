package com.eamh.garmentreviews.ui.reviewdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReviewDetailViewModelFactory(private val reviewId: Int) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ReviewDetailFragmentViewModel(reviewId) as T
    }
}