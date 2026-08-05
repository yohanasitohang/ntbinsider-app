package com.yohana.ntbinsider.ui.theme.panel

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yohana.ntbinsider.data.ObjectEntity
import com.yohana.ntbinsider.model.ViewModelHome
import com.yohana.ntbinsider.ui.theme.widget.*
import com.yohana.ntbinsider.utils.ResponseState

@Composable
fun HomePanel (navController: NavController, scaffoldState: ScaffoldState) {
    val viewModelHome = hiltViewModel<ViewModelHome>()
    val homeUiState by viewModelHome.homeUiState

    viewModelHome.allTour.collectAsState(ResponseState.Loading).value.let { responsestate ->
        when (responsestate) {
            is ResponseState.Loading -> Loader()
            is ResponseState.Error -> ErrorView()
            is ResponseState.Success -> {
                HomeContent(
                    listTour = responsestate.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    query = homeUiState.query,
                    onQueryChange = viewModelHome::onQueryChange,
                    onUpdateFavoriteTour = viewModelHome::updateFavoriteTour
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    listTour: List<ObjectEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    query: String,
    onQueryChange: (String) -> Unit,
    onUpdateFavoriteTour: (id: Int, isFavorite: Boolean) -> Unit
) {
    Column {
        SearchBar(query = query, onQueryChange = onQueryChange)
        when (listTour.isEmpty()) {
            true -> EmptyView()
            false -> AvailableView(listTour, navController, scaffoldState, onUpdateFavoriteTour)
        }
    }
}