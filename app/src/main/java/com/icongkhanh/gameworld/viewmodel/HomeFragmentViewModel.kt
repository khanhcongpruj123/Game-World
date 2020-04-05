package com.icongkhanh.gameworld.viewmodel

import androidx.lifecycle.*
import com.icongkhanh.common.Result
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.usecase.GetGameDetailUsecase
import com.icongkhanh.gameworld.domain.usecase.GetTopRatingGameUsecase
import com.icongkhanh.gameworld.util.merge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragmentViewModel(
    private val getTopRatingGame: GetTopRatingGameUsecase,
    private val getGameDetail: GetGameDetailUsecase
): ViewModel() {

    private var _listTopRatingGame = MutableLiveData<List<Game>>()
    val listTopRatingGame = _listTopRatingGame.distinctUntilChanged()

    private var _topRatingGame = MutableLiveData<Game>()
    val topRatingGame : LiveData<Game> get() = _topRatingGame

    private var _isError = MutableLiveData<Boolean>(false)
    val isError : LiveData<Boolean> = _isError.distinctUntilChanged()

    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading : LiveData<Boolean> = _isLoading.distinctUntilChanged()

    init {
        loadGame()
    }

    fun loadGame() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _isError.value = false
                _isLoading.value = true
            }
            getTopRatingGame().onEach {
                when(it) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            _isError.value = false
                            _isLoading.value = false
                            if (!it.data.isNullOrEmpty()) {
                                _listTopRatingGame.value = it.data.subList(1, it.data.size)
                                _topRatingGame.value = it.data[0]
                            }
                        }
                    }
                    is Result.Error -> {
                        withContext(Dispatchers.Main) {
                            _isError.value = true
                            _isLoading.value = true
                        }
                    }
                }
            }.collect()
            getGameDetail(_topRatingGame.value!!.id).collect {
                when(it) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            _isError.value = false
                            _isLoading.value = false
                            _topRatingGame.value = _topRatingGame.value?.merge(it.data)
                        }
                    }
                    is Result.Error -> {
                        withContext(Dispatchers.Main) {
                            _isError.value = true
                            _isLoading.value = true
                        }
                    }
                }
            }
        }
    }

    fun getTopGame(): Game {
        return _topRatingGame.value!!
    }
}