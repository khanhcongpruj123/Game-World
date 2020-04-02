package com.icongkhanh.common

import android.view.View

fun View.hideOrShow(isShow: Boolean) {
    this.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
}