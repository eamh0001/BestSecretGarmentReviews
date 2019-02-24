package com.eamh.garmentreviews.ui.reviews.item

import com.eamh.garmentreviews.model.Review

class DataListItem(val review: Review): ListItem {
    override fun getType(): ListItem.Type = ListItem.Type.DATA
}