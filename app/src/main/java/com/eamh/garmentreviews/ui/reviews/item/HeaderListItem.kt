package com.eamh.garmentreviews.ui.reviews.item

class HeaderListItem(val text: String): ListItem {
    override fun getType(): ListItem.Type = ListItem.Type.HEADER
}