package com.icongkhanh.gameworld.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.usecase.GetGameDetailUsecase
import com.icongkhanh.gameworld.util.merge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameDetailFragmentViewModel(
    val getGameDetail: GetGameDetailUsecase
) : ViewModel() {

    private var _game = MutableLiveData<Game>()
    val game : LiveData<Game> = _game.distinctUntilChanged()

    fun loadGameDetail() {
        viewModelScope.launch {
            Log.d("ViewModel", "${_game.value}")
            val id = _game.value?.id
            id?.let {
                getGameDetail(it)
                    .onEach {
                        when(it) {
                            is Result.Success -> withContext(Dispatchers.Main) {
                                val data = it.data
                                data?.let {
                                    _game.value = _game.value?.merge(data)
                                }
                            }
                        }
                    }.collect()
            }
        }
    }

    fun initial(game: Game) {
        _game.value = game
        loadGameDetail()
    }
}