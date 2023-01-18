package com.craft404.maximus.util

import android.view.View

interface ClickListener<T> {
    fun onClick(v: View? = null, item: T, position: Int? = null)
}