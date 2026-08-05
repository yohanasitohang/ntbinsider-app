package com.yohana.ntbinsider.ui.theme.panel

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yohana.ntbinsider.data.ObjectEntity
import com.yohana.ntbinsider.model.ViewModelFavorite
import com.yohana.ntbinsider.ui.theme.widget.AvailableView
import com.yohana.ntbinsider.ui.theme.widget.EmptyView
import com.yohana.ntbinsider.ui.theme.widget.ErrorView
import com.yohana.ntbinsider.ui.theme.widget.Loader
import com.yohana.ntbinsider.utils.ResponseState

@Composable
fun FavoritePanel (navController: NavController, scaffoldState: ScaffoldState) {
    val viewModelFavorite = hiltViewModel<ViewModelFavorite>()

    viewModelFavorite.allFavoriteTour.collectAsState(ResponseState.Loading).value.let { responsestate ->
        when (responsestate) {
            is ResponseState.Loading -> Loader()
            is ResponseState.Error -> ErrorView()
            is ResponseState.Success -> {
                FavoriteContent(
                    listFavoriteTour = responsestate.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    onUpdateFavoriteTour = viewModelFavorite::updateFavoriteTour
                )
            }
        }
    }
}

@Composable
fun FavoriteContent(
    listFavoriteTour: List<ObjectEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteTour: (id: Int, isFavorite: Boolean) -> Unit
) {
    when (listFavoriteTour.isEmpty()) {
        true -> EmptyView()
        false -> AvailableView(listFavoriteTour, navController, scaffoldState, onUpdateFavoriteTour)
    }
}