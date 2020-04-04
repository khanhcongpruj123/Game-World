package com.icongkhanh.gameworld.viewmodel

import androidx.annotation.UiThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged

class TabContainerViewModel: ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading : LiveData<Boolean> = _isLoading.distinctUntilChanged()

    @UiThread
    fun isLoading(isShow: Boolean) {
        _isLoading.value = isShow
    }
}