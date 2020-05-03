package com.icongkhanh.gameworld.viewmodel

import androidx.lifecycle.*
import com.icongkhanh.common.Result
import com.icongkhanh.common.event.Event
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.model.Genre
import com.icongkhanh.gameworld.domain.usecase.BookmarkGameUsecase
import com.icongkhanh.gameworld.domain.usecase.GetGameDetailUsecase
import com.icongkhanh.gameworld.domain.usecase.GetGameOfGenreUsecase
import com.icongkhanh.gameworld.domain.usecase.UnBookMarkGameUsecase
import com.icongkhanh.gameworld.model.*
import com.icongkhanh.gameworld.util.LogTool
import com.icongkhanh.gameworld.util.merge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameDetailFragmentViewModel(
    val getGameDetail: GetGameDetailUsecase,
    val getGameOfGenre: GetGameOfGenreUsecase,
    val bookmarkGame: BookmarkGameUsecase,
    val unBookMark: UnBookMarkGameUsecase
) : ViewModel() {

    private var initGame: Game? = null

    private var _game = MutableLiveData<Game>()

    val gameUiModel: LiveData<GameDetailUiModel>
        get() = _game.map { game ->
            GameDetailUiModel(
                name = game.name,
                rating = game.rating,
                reviewCount = game.ratingsCount,
                suggestionCount = game.suggestionsCount,
                imgUrl = game.imgUrl,
                clipUrl = game.clipUrl,
                clipPreviewUrl = game.clipPreviewUrl,
                description = game.description,
                recommened = game.platforms.firstOrNull()?.requirementRecommended ?: "",
                requirement = game.platforms.firstOrNull()?.requirementMin ?: "",
                listGenre = game.genre.map {
                    ItemGenreUiModel(
                        name = it.name,
                        onClick = {

                        }
                    )
                },
                listScreenShot = game.screenShort.map {
                    ScreenshotModelUi(
                        url = it,
                        onClick = {
                            emitNavigateViewImage(it)
                        }
                    )
                },
                onClickBuy = {
                    emitNavigateBuy(game)
                },
                onClickBookMark = {
                    LogTool.d("AppLog", "On Click Bookmark: ${bookMark.value}")
                    viewModelScope.launch {
                        when (bookMark.value) {
                            BookMarkGameDetailState.UnBookMarkMode -> {
                                LogTool.d("AppLog", "unbookmark")
                                unBookMark(game.id).collect { res ->
                                    when (res) {
                                        is Result.Success -> {
                                            _game.value = _game.value?.copy(
                                                isBookmark = false
                                            )
                                        }
                                    }
                                }
                            }
                            else -> {
                                LogTool.d("AppLog", "bookmark")
                                bookmarkGame(game.id).collect { res ->
                                    when (res) {
                                        is Result.Success -> {
                                            _game.value = _game.value?.copy(
                                                isBookmark = true
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                onClickClip = {
                    emitNavigateToViewClip(game.clipUrl)
                },
                isBookmark = game.isBookmark
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

//    val bookMark: LiveData<BookMarkGameDetailState> = _game.switchMap {
//            liveData {
//                emit(if (it.isBookmark) BookMarkGameDetailState.UnBookMarkMode else BookMarkGameDetailState.BookMarkMode)
//            }
//        }

    val bookMark = _game.map {
        if (it.isBookmark) BookMarkGameDetailState.UnBookMarkMode else BookMarkGameDetailState.BookMarkMode
    }

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
        viewModelScope.launch(Dispatchers.Default) {
            val id = initGame?.id
            id?.let {
                getGameDetail(it)
                    .onEach {
                        when(it) {
                            is Result.Success -> withContext(Dispatchers.Main) {
                                val data = it.data
                                data?.let {
                                    LogTool.d("GameDetailVM", "before: ${it}")
                                    val afterMerge = initGame?.merge(it)
                                    LogTool.d("GameDetailVM", "after: ${afterMerge}")
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
            val idGenre = getCurrentGame()?.genre?.firstOrNull()?.id
            idGenre?.let {
                getGameOfGenre(idGenre).onEach {
                    if (it is Result.Success) {
                        withContext(Dispatchers.Main) { _listMoreGame.value = it.data }
                    }
                }.collect()
            }
        }
    }

    fun getCurrentGame() = _game.value

    private fun emitNavigateBuy(game: Game) {
        _navigateToBuy.value = Event(game)
    }

//    private fun emitBookmark(isSuccess: Boolean) {
//        _bookMark.value = Event(isSuccess)
//    }

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