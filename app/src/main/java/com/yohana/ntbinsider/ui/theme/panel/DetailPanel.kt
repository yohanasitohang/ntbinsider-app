package com.yohana.ntbinsider.ui.theme.panel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import com.yohana.ntbinsider.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.yohana.ntbinsider.data.ObjectEntity
import com.yohana.ntbinsider.model.ViewModelDetail
import com.yohana.ntbinsider.utils.ResponseState
import com.yohana.ntbinsider.utils.RatingUtil
import com.yohana.ntbinsider.utils.NumberUtil
import kotlinx.coroutines.launch

@Composable
fun DetailPanel (tourId: Int, navController: NavController, scaffoldState: ScaffoldState) {
    val detailViewModel = hiltViewModel<ViewModelDetail>()

    LaunchedEffect(key1 = tourId) {
        detailViewModel.getTour(tourId)
    }

    val responsestate by detailViewModel.tour.collectAsState()
    when (responsestate) {
        is ResponseState.Loading -> {
        }
        is ResponseState.Error -> {
        }
        is ResponseState.Success -> {
            val data = (responsestate as ResponseState.Success).data
            DetailContent(
                tour = data,
                navController = navController,
                scaffoldState = scaffoldState,
                onUpdateFavoriteTour = detailViewModel::updateFavoriteTour
            )
        }
    }
}

@Composable
fun DetailContent(
    tour: ObjectEntity,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteTour: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name, description, location, photo, rating, totalReview, isFavorite) = tour

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            Box {
                AsyncImage(
                    model = photo,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.baseline_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 8.dp))
                        .background(MaterialTheme.colors.primaryVariant)
                        .align(Alignment.BottomStart),
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            text = location,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h5
                    )

                    Icon(
                        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        tint = if (isFavorite) Color.Red else MaterialTheme.colors.onSurface,
                        contentDescription = name,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(100))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onUpdateFavoriteTour(id ?: 0, !isFavorite)
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "$name ${if (isFavorite) "removed from" else "added as"} favorite ",
                                        actionLabel = "Dismiss",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val nRate = RatingUtil(rating)
                    repeat(nRate) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            contentDescription = name,
                            tint = Color(0xFFFFCC00)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$rating/5 (${NumberUtil(totalReview)} reviews)",
                        style = MaterialTheme.typography.body2
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.body1,
                    lineHeight = 24.sp,
                )
            }
        }

        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .testTag("back_button")
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
            )
        }
    }
}