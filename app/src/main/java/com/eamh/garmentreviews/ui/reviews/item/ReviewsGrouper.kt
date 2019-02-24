package com.eamh.garmentreviews.ui.reviews.item

import com.eamh.garmentreviews.model.Review
import com.eamh.garmentreviews.utils.toFormatString

class ReviewsGrouper(private val reviews: List<Review>) {
    enum class By{
        DATE
    }

    fun getGrouped(groupedBy: By): List<ListItem> {
        return when(groupedBy){
            By.DATE -> computeGroupingByDate()
        }
    }

    private fun computeGroupingByDate(): List<ListItem> {
        return reviews
                .sortedByDescending { it.date }
                .groupBy { it.date.toFormatString("MMMM YYYY")}
                .flatMap {
                    mutableListOf<ListItem>().apply {
                        add(HeaderListItem(it.key))
                        addAll(it.value.map { DataListItem(it) })
                    }
                }
    }
}
