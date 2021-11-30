package com.zyp.codetest.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

object Utils {
    fun ImageView.loadImageFromGlide(url: String?) {
        if (url != null) {
            Glide.with(this)
                .load(url)
                .error(android.R.drawable.ic_menu_close_clear_cancel)
                .fitCenter()
                .placeholder(android.R.drawable.ic_menu_crop)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }

    }

}