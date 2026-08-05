package com.yohana.ntbinsider.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yohana.ntbinsider.data.ObjectEntity
import com.yohana.ntbinsider.data.ObjectStore
import com.yohana.ntbinsider.repository.ObjectRepository
import com.yohana.ntbinsider.ui.theme.panel.HomeUiState
import com.yohana.ntbinsider.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor(private val objectRepository: ObjectRepository) : ViewModel() {
    private val _allTour = MutableStateFlow<ResponseState<List<ObjectEntity>>>(ResponseState.Loading)
    val allTour = _allTour.asStateFlow()

    private val _homeUiState = mutableStateOf(HomeUiState())
    val homeUiState: State<HomeUiState> = _homeUiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            objectRepository.getAllTour().collect { tour ->
                when (tour.isEmpty()) {
                    true -> objectRepository.insertAllTour(ObjectStore.dummy)
                    else -> _allTour.value = ResponseState.Success(tour)
                }
            }
        }
    }

    private fun searchTour(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            objectRepository.searchTour(query)
                .catch { _allTour.value = ResponseState.Error(it.message.toString()) }
                .collect { _allTour.value = ResponseState.Success(it) }
        }
    }

    fun updateFavoriteTour(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            objectRepository.updateFavoriteTour(id, isFavorite)
        }
    }

    fun onQueryChange(query: String) {
        _homeUiState.value = _homeUiState.value.copy(query = query)
        searchTour(query)
    }
}