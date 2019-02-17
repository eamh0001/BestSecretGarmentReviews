package com.eamh.garmentreviews.model

data class Review(
        val id: Int,
        val type: ReviewType,
        val name: String,
        val designer: String,
        val description: String,
        val price: Double,
        val picture: String,
        val reviewers: List<Reviewer>
)