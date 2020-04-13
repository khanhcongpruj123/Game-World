package com.icongkhanh.gameworld.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.icongkhanh.common.Result
import com.icongkhanh.common.event.Event
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

    private var _isError = MutableLiveData<Event<Boolean>>(Event(false))
    val isError: LiveData<Event<Boolean>>
        get() = _isError

    fun loadGameDetail() {
        viewModelScope.launch {
            val id = _game.value?.id
            id?.let {
                getGameDetail(it)
                    .onEach {
                        when(it) {
                            is Result.Success -> withContext(Dispatchers.Main) {
                                val data = it.data
                                data?.let {
                                    Log.d("GameDetailVM", "before: ${it.stores?.get(0)?.website}")
                                    val afterMerge = _game.value?.merge(it)
                                    Log.d("GameDetailVM", "after: ${afterMerge?.stores?.get(0)?.website}")
                                    _game.value = afterMerge
                                }
                            }
                            is Result.Error -> withContext(Dispatchers.Main) {
                                _isError.value = Event(true)
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

    fun getCurrentGame() = _game.value!!
}