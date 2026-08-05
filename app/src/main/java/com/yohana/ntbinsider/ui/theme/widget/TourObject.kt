package com.yohana.ntbinsider.ui.theme.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import com.yohana.ntbinsider.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.yohana.ntbinsider.data.ObjectEntity
import com.yohana.ntbinsider.ui.theme.navigation.NavPanel
import com.yohana.ntbinsider.utils.RatingUtil
import com.yohana.ntbinsider.utils.NumberUtil
import kotlinx.coroutines.launch

@Composable
fun AvailableView(
    listTour: List<ObjectEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteTourism: (id: Int, isFavorite: Boolean) -> Unit,
) {
    LazyColumn {
        items(listTour, key = { it.name }) { tour ->
            TourObject(tour, navController, scaffoldState, onUpdateFavoriteTourism)
        }
    }
}

@Composable
fun TourObject(
    tourism: ObjectEntity,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteTour: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name, _, location, photo, rating, totalReview, isFavorite) = tourism

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .border(1.dp, Color.LightGray.copy(0.5f), MaterialTheme.shapes.small)
            .clickable { navController.navigate(NavPanel.Detail.createRoute(id ?: 0)) },
    ) {
        Column {
            Box {
                AsyncImage(
                    model = photo,
                    contentDescription = name,
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(R.drawable.baseline_image),
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomEnd = 8.dp))
                        .background(MaterialTheme.colors.primaryVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = location,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h6
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
            }
        }
    }
}