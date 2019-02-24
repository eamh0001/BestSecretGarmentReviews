package com.eamh.garmentreviews.model

import java.util.*

data class Review(
        val id: Int,
        val type: ReviewType,
        val name: String,
        val designer: String,
        val description: String,
        val price: Double,
        val picture: String,
        val date: Date,
        val reviewers: List<Reviewer>
)