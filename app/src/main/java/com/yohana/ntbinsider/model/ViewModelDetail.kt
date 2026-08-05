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
class ViewModelDetail @Inject constructor(private val objectRepository: ObjectRepository) : ViewModel() {
    private val _tour = MutableStateFlow<ResponseState<ObjectEntity>>(ResponseState.Loading)
    val tour = _tour.asStateFlow()

    fun getTour(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            objectRepository.getTour(id)
                .catch { _tour.value = ResponseState.Error(it.message.toString()) }
                .collect { _tour.value = ResponseState.Success(it) }
        }
    }

    fun updateFavoriteTour(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            objectRepository.updateFavoriteTour(id, isFavorite)
        }
    }
}