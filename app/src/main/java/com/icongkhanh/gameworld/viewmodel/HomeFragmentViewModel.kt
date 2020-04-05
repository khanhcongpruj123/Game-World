package com.icongkhanh.gameworld.viewmodel

import androidx.lifecycle.*
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

    private var _listTopRatingGame = MutableLiveData<List<Game>>()
    val listTopRatingGame = _listTopRatingGame.distinctUntilChanged()

    private var _topRatingGame = MutableLiveData<Game>()
    val topRatingGame : LiveData<Game> get() = _topRatingGame

    init {
        viewModelScope.launch {
            getTopRatingGame().onEach {
                when(it) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            if (!it.data.isNullOrEmpty()) {
                                _listTopRatingGame.value = it.data.subList(1, it.data.size)
                                _topRatingGame.value = it.data[0]
                            }
                        }
                    }
                }
            }.collect()
        }
    }
}