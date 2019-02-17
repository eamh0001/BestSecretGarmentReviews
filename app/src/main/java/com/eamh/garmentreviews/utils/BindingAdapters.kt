package com.eamh.garmentreviews.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eamh.garmentreviews.R
import com.github.florent37.picassopalette.PicassoPalette
import com.squareup.picasso.Picasso

@BindingAdapter("adapter")
fun RecyclerView.setAdapter(adapter: RecyclerView.Adapter<*>) {
    this.adapter = adapter
}

@BindingAdapter("imageResource")
fun ImageView.setImageResource(resourceId: Int) {
    setImageResource(resourceId)
}

@BindingAdapter("loadImageFromUri")
fun ImageView.setLoadImageFromUri(path: String?) {
    if (path != null) {
        Picasso.get()
                .load(path)
                .placeholder(R.drawable.autorenew)
                .error(R.drawable.cancel)
                .into(this,
                        PicassoPalette.with(path, this@setLoadImageFromUri)
                                .use(PicassoPalette.Profile.MUTED_LIGHT)
                                .intoBackground(this@setLoadImageFromUri))
    }
}

@BindingAdapter("textId")
fun TextView.setTextId(textId: Int){
    if (textId != 0){
        this.setText(textId)
    }else
        this.setText(R.string.empty)
}