package com.icongkhanh.common

import android.view.View

fun View.showOrHide(isShow: Boolean) {
    this.visibility = if (isShow) View.VISIBLE else View.INVISIBLE
}