package rs.edu.raf.cataquiz.catinfo.detail

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.SubcomposeAsyncImage
import rs.edu.raf.cataquiz.R
import rs.edu.raf.cataquiz.catinfo.detail.CatDetailContract.CatDetailUiState
import rs.edu.raf.cataquiz.catinfo.detail.model.ImageUIModel
import rs.edu.raf.cataquiz.components.AppIconButton
import rs.edu.raf.cataquiz.components.CustomCard
import rs.edu.raf.cataquiz.components.RatingBar
import rs.edu.raf.cataquiz.ui.theme.Indigo900
import kotlin.math.abs


@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.catDetail(
    route: String,
    arguments: List<NamedNavArgument>,
    onClose: () -> Unit,
) = composable(
    route = route,
    arguments = arguments,
    enterTransition = { slideInVertically { it } },
    popExitTransition = { slideOutVertically { it } },
) { navBackStackEntry ->
    val viewModel: CatDetailViewModel = hiltViewModel<CatDetailViewModel>(navBackStackEntry)
    val state = viewModel.state.collectAsState()
    CatDetailScreen(state = state.value, onClose = onClose)
}

@ExperimentalMaterial3Api
@Composable
fun CatDetailScreen(
    state: CatDetailUiState,
    onClose: () -> Unit,
) {
    Scaffold(
        topBar = {
            if (!state.loading) {
                TopAppBar(title = {
                    if (state.data != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = state.data.name)
                            if (state.data.isRare) {
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = "Rare",
                                    color = Color.White,
                                    modifier = Modifier.background(
                                        Color(Indigo900.toArgb()), shape = RoundedCornerShape(4.dp)
                                    )
                                )

                            }
                            val uriHandler = LocalUriHandler.current
                            Spacer(modifier = Modifier.width(16.dp))
                            IconButton(onClick = {
                                val wikiUrl = state.data.wikipediaUrl
                                uriHandler.openUri(wikiUrl)
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.wiki),
                                    contentDescription = "Wikipedia shortcut"
                                )
                            }
                        }
                    }
                }, navigationIcon = {
                    AppIconButton(
                        onClick = { onClose() }, imageVector = Icons.AutoMirrored.Filled.ArrowBack
                    )
                })
            } else {
                TopAppBar(title = {
                    Text(text = "Cats")
                }, navigationIcon = {
                    AppIconButton(
                        onClick = { onClose() }, imageVector = Icons.AutoMirrored.Filled.ArrowBack
                    )
                })
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            if (state.loading) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Loading...",
                        modifier = Modifier.padding(vertical = 20.dp),
                    )
                    CircularProgressIndicator()
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        if (state.data != null) {
                            if (state.data.images.isNotEmpty()) {
                                Carousel(
                                    images = state.data.images,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .requiredHeight(300.dp)
                                )
                            } else {
                                Text(
                                    text = "No images found :(", modifier = Modifier.size(20.dp)
                                )
                            }
                            state.data.description.let { str ->
                                CustomCard(text = "$str\r\n\r\nAverage weight is: ${state.data.averageWeight} kg")
                            }
                            CustomCard(text = "The expected lifespan is ${state.data.lifeSpan} years")
                            LazyRow {
                                items(items = state.data.temperament) {
                                    SuggestionChip(onClick = {}, label = {
                                        Text(text = it.mapIndexed { index, c ->
                                            if (index == 0) c.uppercaseChar() else c
                                        }.joinToString(separator = "") {
                                            it.toString()
                                        })
                                    }, modifier = Modifier.padding(4.dp)
                                    )
                                }
                            }
                            CustomCard(
                                text = "Originates from: ${
                                    state.data.countriesOfOrigin.joinToString(", ")
                                }"
                            )
                            ElevatedCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Row(
                                    modifier = Modifier.padding(20.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    RatingBar(rating = state.data.adaptability.toFloat())
                                    Text(
                                        "Adaptability",
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                                Row(
                                    modifier = Modifier.padding(20.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    RatingBar(rating = state.data.affectionLevel.toFloat())
                                    Text(
                                        "Affection level",
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                                Row(
                                    modifier = Modifier.padding(20.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    RatingBar(rating = state.data.childFriendly.toFloat())
                                    Text(
                                        "Child friendliness",
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                                Row(
                                    modifier = Modifier.padding(20.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    RatingBar(rating = state.data.dogFriendly.toFloat())
                                    Text(
                                        "Dog friendliness",
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                                Row(
                                    modifier = Modifier.padding(20.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    RatingBar(rating = state.data.energyLevel.toFloat())
                                    Text(
                                        "Energy level",
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Shamelessly copied from https://stackoverflow.com/questions/76731181
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(images: List<ImageUIModel>, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(
        pageCount = { images.size },
        initialPage = 0,
        initialPageOffsetFraction = 0f,
    )

    HorizontalPager(
        state = pagerState, modifier = modifier.fillMaxSize(), pageSpacing = 10.dp
    ) { page ->
        val currentImage = images[page]
        val offset: Float = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
        SubcomposeAsyncImage(model = currentImage.url,
            contentDescription = currentImage.id,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(300.dp)
                .graphicsLayer {
                    lerp(
                        start = 0.65f, stop = 1f, fraction = abs(offset).coerceIn(0f, 1f)
                    )
                    // Change the alpha of the page depending on its position
                    alpha = lerp(
                        start = 0.2f, stop = 1f, fraction = 1f - abs(offset)
                    )
                }
                .zIndex(1f - abs(offset)))
    }
}
