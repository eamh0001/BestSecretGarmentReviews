package com.eamh.garmentreviews.ui.reviews.item

interface ListItem {

    enum class Type {
        DATA,
        HEADER
    }

    fun getType():Type
}