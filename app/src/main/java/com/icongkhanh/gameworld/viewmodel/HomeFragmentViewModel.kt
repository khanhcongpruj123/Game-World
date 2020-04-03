package com.icongkhanh.gameworld.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.usecase.GetTopRatingGameUsecase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragmentViewModel(
    getTopRatingGame: GetTopRatingGameUsecase
): ViewModel() {

    private var _topRatingGame = MutableLiveData<List<Game>>()
    val topRatingGame = _topRatingGame.distinctUntilChanged()

    init {
        viewModelScope.launch {
            getTopRatingGame().onEach {
                when(it) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) { _topRatingGame.value = it.data }
                    }
                }
            }.collect()
        }
    }
}