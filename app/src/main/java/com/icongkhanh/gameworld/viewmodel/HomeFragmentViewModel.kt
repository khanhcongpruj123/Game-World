package com.icongkhanh.gameworld.viewmodel

import androidx.lifecycle.*
import com.icongkhanh.common.Result
import com.icongkhanh.common.event.Event
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.usecase.GetGameDetailUsecase
import com.icongkhanh.gameworld.domain.usecase.GetTopRatingGameUsecase
import com.icongkhanh.gameworld.model.ItemTopGameUiModel
import com.icongkhanh.gameworld.model.ListTopGameUiModel
import com.icongkhanh.gameworld.model.TopGameUiModel
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

    private var listTopRatingGame = MutableLiveData<List<Game>>()

    val listTopRatingGameUiModel: LiveData<ListTopGameUiModel>
        get() = listTopRatingGame.map {
            ListTopGameUiModel(
                list = it.map { game ->
                    ItemTopGameUiModel(
                        id = game.id,
                        name = game.name,
                        listGenre = game.genre.map { it.name },
                        clipPreviewUrl = game.clipPreviewUrl,
                        clipUrl = game.clipUrl,
                        thumbnailUrl = game.imgUrl,
                        rating = game.rating,
                        ratingCount = game.ratingsCount,
                        onClick = {
                            emitNavigateToGameDetail(game)
                        }
                    )
                }
            )
        }

    private var topRatingGame = MutableLiveData<Game>()

    val topRatingGameUiModel: LiveData<TopGameUiModel>
        get() = topRatingGame.map {
            TopGameUiModel(
                nameGame = it.name,
                clipUrl = it.clipUrl,
                clipPreviewUrl = it.clipPreviewUrl,
                starPoint = it.rating,
                onClickDetail = {
                    emitNavigateToGameDetail(it)
                },
                onClickBuy = {
                    emitNavigateToBuy(it)
                }
            )
        }

    private var _isError = MutableLiveData<Event<Boolean>>().apply { value = Event(false) }
    val isError: LiveData<Event<Boolean>>
        get() = _isError

    private var _isLoading = MutableLiveData<Event<Boolean>>().apply { value = Event(false) }
    val isLoading: LiveData<Event<Boolean>>
        get() = _isLoading

    private var _navigateToGameDetail = MutableLiveData<Event<Game>>()
    val navigateToGameDetail: LiveData<Event<Game>>
        get() = _navigateToGameDetail

    private val _navigateToBuyGame = MutableLiveData<Event<Game>>()
    val navigateToBuyGame: LiveData<Event<Game>>
        get() = _navigateToBuyGame

    private var page = 1L

    fun start() {
        loadGame()
    }

    fun loadGame() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                _isError.value = Event(false)
                _isLoading.value = Event(true)
            }
            //loading list top game
            getTopRatingGame().onEach {
                when(it) {
                    is Result.Success -> {
                        withContext(Dispatchers.Main) {
                            _isLoading.value = Event(false)

                            if (!it.data.isNullOrEmpty()) {
                                val listTopGame = it.data.subList(1, it.data.size).toMutableList()
                                emitListTopGame(listTopGame)
                                emitTopGame(it.data[0])
                            }
                        }
                    }
                    is Result.Error -> {
                        withContext(Dispatchers.Main) {
                            _isError.value = Event(true)
                            _isLoading.value = Event(true)
                        }
                    }
                }
            }.collect()
            // get top game detail
            topRatingGame.value?.let { topGame ->
                getGameDetail(topGame.id).collect {
                    when (it) {
                        is Result.Success -> {
                            withContext(Dispatchers.Main) {
                                _isLoading.value = Event(false)

                                val topGame = topRatingGame.value?.merge(it.data)
                                emitTopGame(topGame)
                            }
                        }
                        is Result.Error -> {
                            withContext(Dispatchers.Main) {
                                _isError.value = Event(true)
                                _isLoading.value = Event(true)
                            }
                        }
                    }
                }
            }
        }
    }

    fun getTopGame(): Game? = topRatingGame.value


    private fun emitListTopGame(list: List<Game>?) {
        if (list == null) return
        listTopRatingGame.value = list
    }

    private fun emitTopGame(game: Game?) {
        if (game == null) return
        topRatingGame.value = game
    }

    private fun emitNavigateToGameDetail(game: Game?) {
        if (game == null) return
        _navigateToGameDetail.value = Event(game)
    }

    private fun emitNavigateToBuy(game: Game?) {
        if (game == null) return
        _navigateToBuyGame.value = Event(game)
    }

    fun nextPageTopGame() {
//        viewModelScope.launch {
//            getTopRatingGame(page++).onEach {res ->
//                when(res) {
//                    is Result.Success -> {
//                        withContext(Dispatchers.Main) {
//                            if (!res.data.isNullOrEmpty()) {
//                                _listTopRatingGame.value?.let {
//                                    val listGameTopGame = mutableListOf<Game>()
//                                    listGameTopGame.addAll(it)
//                                    listGameTopGame.addAll(res.data)
//                                    _listTopRatingGame.value = listGameTopGame
//                                }
//                            }
//                        }
//                    }
//                    is Result.Error -> {
//                        withContext(Dispatchers.Main) {
//                            _isError.value = true
//                            _isLoading.value = true
//                        }
//                    }
//                }
//            }.collect()
//        }
    }
}