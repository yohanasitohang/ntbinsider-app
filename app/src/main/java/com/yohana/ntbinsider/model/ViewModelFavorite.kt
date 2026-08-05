package com.yohana.ntbinsider.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yohana.ntbinsider.data.ObjectEntity
import com.yohana.ntbinsider.repository.ObjectRepository
import com.yohana.ntbinsider.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelFavorite @Inject constructor(private val objectRepository: ObjectRepository) : ViewModel() {
    private val _allFavoriteTour = MutableStateFlow<ResponseState<List<ObjectEntity>>>(ResponseState.Loading)
    val allFavoriteTour = _allFavoriteTour.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            objectRepository.getAllFavoriteTour()
                .catch { _allFavoriteTour.value = ResponseState.Error(it.message.toString()) }
                .collect { _allFavoriteTour.value = ResponseState.Success(it) }
        }
    }

    fun updateFavoriteTour(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            objectRepository.updateFavoriteTour(id, isFavorite)
        }
    }
}