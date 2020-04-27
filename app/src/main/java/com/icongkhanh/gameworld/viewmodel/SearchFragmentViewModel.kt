package com.icongkhanh.gameworld.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.icongkhanh.common.Result
import com.icongkhanh.common.event.Event
import com.icongkhanh.gameworld.domain.model.Game
import com.icongkhanh.gameworld.domain.usecase.SearchGameUsecase
import com.icongkhanh.gameworld.model.SearchGameUiModel
import com.icongkhanh.gameworld.model.SearchViewUiModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

class SearchFragmentViewModel(
    val searchGameUsecase: SearchGameUsecase
) : ViewModel() {

    private var job: Job? = null

    private val _loadingViewVisiable = MutableLiveData(Event(false))
    val loadingViewVisiale: LiveData<Event<Boolean>>
        get() = _loadingViewVisiable

    private val _keySearch = MutableLiveData("")
    val keySearch: LiveData<String>
        get() = _keySearch

    private val _searchViewUiModel = MutableLiveData(
        SearchViewUiModel {
            _keySearch.value = it
        }
    )
    val searchViewUiModel: LiveData<SearchViewUiModel>
        get() = _searchViewUiModel

    val listSearchGame: LiveData<List<SearchGameUiModel>> = _keySearch.switchMap { name ->
        liveData {
            Log.d("AppLog", "start search")
            _loadingViewVisiable.value = Event(true)
            if (name.isNullOrBlank()) {
                emit(emptyList<SearchGameUiModel>())
                _loadingViewVisiable.value = Event(false)
            } else {
                searchGameUsecase(name)
                    .onEach { result ->
                        when (result) {
                            is Result.Success -> {
                                Log.d("AppLog", "start success")
                                emit(
                                    result.data.map {
                                        SearchGameUiModel(
                                            it.id,
                                            it.imgUrl,
                                            it.name
                                        ) {
                                            _navigateGameDetail.value = Event(it)
                                        }
                                    }
                                )
                                _loadingViewVisiable.value = Event(false)
                            }
                        }
                    }.collect()
            }
        }
    }

    private val _navigateGameDetail = MutableLiveData<Event<Game>>()
    val navigateGameDetail: LiveData<Event<Game>>
        get() = _navigateGameDetail

//    fun searchGame(name: String?) {
//        Log.d("AppLog", "Start Search!")
//        _loadingViewVisiable.value = Event(true)
//        job?.cancel()
//        job = viewModelScope.launch {
//            if (name.isNullOrBlank()) withContext(Dispatchers.Main) {
//                _listSearchGame.value = emptyList()
//                _loadingViewVisiable.value = Event(false)}
//            else {
//                searchGameUsecase(name)
//                    .onEach { result ->
//                        when(result) {
//                            is Result.Success -> {
//                                withContext(Dispatchers.Main) {
//                                    _listSearchGame.value = result.data.map {
//                                        SearchGameUiModel(
//                                            it.id,
//                                            it.imgUrl,
//                                            it.name
//                                        ) {
//                                            _navigateGameDetail.value = Event(it)
//                                        }
//                                    }
//                                    _loadingViewVisiable.value = Event(false)
//                                }
//                            }
//                        }
//                    }
//                    .collect()
//            }
//        }
//    }
}