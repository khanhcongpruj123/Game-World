package com.icongkhanh.gameworld.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.icongkhanh.common.Result
import com.icongkhanh.common.event.Event
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.model.Genre
import com.icongkhanh.gameworld.domain.usecase.GetGameDetailUsecase
import com.icongkhanh.gameworld.domain.usecase.GetGameOfGenreUsecase
import com.icongkhanh.gameworld.model.GameDetailUiModel
import com.icongkhanh.gameworld.model.ItemGenreUiModel
import com.icongkhanh.gameworld.model.ItemMoreGameUiModel
import com.icongkhanh.gameworld.model.ScreenshotModelUi
import com.icongkhanh.gameworld.util.merge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameDetailFragmentViewModel(
    val getGameDetail: GetGameDetailUsecase,
    val getGameOfGenre: GetGameOfGenreUsecase
) : ViewModel() {

    private var _game = MutableLiveData<Game>()

    val gameUiModel: LiveData<GameDetailUiModel>
        get() = _game.map {
            GameDetailUiModel(
                name = it.name,
                rating = it.rating,
                reviewCount = it.ratingsCount,
                suggestionCount = it.suggestionsCount,
                imgUrl = it.imgUrl,
                clipUrl = it.clipUrl,
                clipPreviewUrl = it.clipPreviewUrl,
                description = it.description,
                recommened = it.platforms.firstOrNull()?.requirementRecommended ?: "",
                requirement = it.platforms.firstOrNull()?.requirementMin ?: "",
                listGenre = it.genre.map {
                    ItemGenreUiModel(
                        name = it.name,
                        onClick = {

                        }
                    )
                },
                listScreenShot = it.screenShort.map {
                    ScreenshotModelUi(
                        url = it,
                        onClick = {
                            emitNavigateViewImage(it)
                        }
                    )
                },
                onClickBuy = {
                    emitNavigateBuy(it)
                },
                onClickBookMark = {
                    emitBookmark(it)
                },
                onClickClip = {
                    emitNavigateToViewClip(it.clipUrl)
                }
            )
        }

    private var _listMoreGame = MutableLiveData<List<Game>>()
    val listMoreGameUiModel: LiveData<List<ItemMoreGameUiModel>>
        get() = _listMoreGame.map {
            it.map {
                ItemMoreGameUiModel(
                    name = it.name,
                    imgUrl = it.imgUrl,
                    onClick = {
                        emitNavigateToDetail(it)
                    }
                )
            }
        }

    private var _isError = MutableLiveData<Event<Boolean>>(Event(false))
    val isError: LiveData<Event<Boolean>>
        get() = _isError


    private var _navigateToBuy = MutableLiveData<Event<Game>>()
    val navigateToBuy: LiveData<Event<Game>>
        get() = _navigateToBuy

    private var _bookMark = MutableLiveData<Event<Game>>()
    val bookMark: LiveData<Event<Game>>
        get() = _bookMark

    private var _navigateToViewImage = MutableLiveData<Event<String>>()
    val navigateToViewImage: LiveData<Event<String>>
        get() = _navigateToViewImage

    private var _navigateToGenre = MutableLiveData<Event<Genre>>()
    val navigateToGenre: LiveData<Event<Genre>>
        get() = _navigateToGenre

    private var _navigateToViewClip = MutableLiveData<Event<String>>()
    val navigateToViewClip: LiveData<Event<String>>
        get() = _navigateToViewClip

    private var _navigateToDetail = MutableLiveData<Event<Game>>()
    val navigateToDetail: LiveData<Event<Game>>
        get() = _navigateToDetail

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
        loadMoreGame()
    }

    private fun loadMoreGame() {
        viewModelScope.launch {
            val idGenre = getCurrentGame().genre.firstOrNull()?.id
            idGenre?.let {
                getGameOfGenre(idGenre).onEach {
                    if (it is Result.Success) {
                        withContext(Dispatchers.Main) { _listMoreGame.value = it.data }
                    }
                }.collect()
            }
        }
    }

    fun getCurrentGame() = _game.value!!

    private fun emitNavigateBuy(game: Game) {
        _navigateToBuy.value = Event(game)
    }

    private fun emitBookmark(game: Game) {
        _bookMark.value = Event(game)
    }

    private fun emitNavigateViewImage(url: String) {
        _navigateToViewImage.value = Event(url)
    }

    private fun emitNavigateToGenre(genre: Genre) {
        _navigateToGenre.value = Event(genre)
    }

    private fun emitNavigateToViewClip(url: String) {
        _navigateToViewClip.value = Event(url)
    }

    private fun emitNavigateToDetail(game: Game) {
        _navigateToDetail.value = Event(game)
    }
}