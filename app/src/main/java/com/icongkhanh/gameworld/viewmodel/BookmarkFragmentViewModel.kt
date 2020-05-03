package com.icongkhanh.gameworld.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icongkhanh.common.Result
import com.icongkhanh.common.event.Event
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.usecase.GetAllBookmarkGameUsecase
import com.icongkhanh.gameworld.model.GameBookmarkUiModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookmarkFragmentViewModel(
    val getAllBookmarkGame: GetAllBookmarkGameUsecase
) : ViewModel() {

    private val _listGame = MutableLiveData<List<GameBookmarkUiModel>>()
    val listGame: LiveData<List<GameBookmarkUiModel>> = _listGame

    private val _navigateGameDetail = MutableLiveData<Event<Game>>()
    val navigateGameDetail: LiveData<Event<Game>> = _navigateGameDetail

    init {
        viewModelScope.launch {
            getAllBookmarkGame().collect { res ->
                when (res) {
                    is Result.Success -> {
                        _listGame.value = res.data.map {
                            GameBookmarkUiModel(
                                id = it.id,
                                name = it.name,
                                imgUrl = it.imgUrl,
                                rating = it.rating,
                                onClick = {
                                    _navigateGameDetail.value = Event(it)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}