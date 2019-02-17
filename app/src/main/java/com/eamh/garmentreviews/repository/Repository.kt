package com.eamh.garmentreviews.repository

import com.eamh.garmentreviews.model.Review
import io.reactivex.Single

interface Repository {

    fun getReviews(): Single<List<Review>>
    fun getReview(id: Int): Single<Review>
}