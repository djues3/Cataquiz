package rs.edu.raf.cataquiz.catinfo.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import rs.edu.raf.cataquiz.components.AppIconButton
import rs.edu.raf.cataquiz.components.PhotoPreview

fun NavGraphBuilder.catGallery(
    route: String,
    onClose: () -> Unit,
    onImageClick: (imageId: String) -> Unit,
) = composable(
    route = route,
) {
    val viewModel: CatGalleryViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()


    CatGalleryScreen(state = state.value, onClose = onClose, onImageClick = onImageClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatGalleryScreen(
    onClose: () -> Unit,
    onImageClick: (imageId: String) -> Unit,
    state: CatGalleryContract.CatGalleryUiState,
) {
    Scaffold(
        topBar = {
            MediumTopAppBar(title = { Text(text = "Cat Gallery") }, navigationIcon = {
                AppIconButton(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    onClick = onClose,
                )
            })
        },
    ) { paddingValues ->
        BoxWithConstraints(
            modifier = Modifier,
            contentAlignment = Alignment.BottomCenter,
        ) {
            val screenWidth = this.maxWidth
            val cellSize = (screenWidth / 2) - 4.dp

            if (state.catGallery.images.isEmpty() && state.loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(36.dp),
                    )
                }
            } else {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp),
                    columns = GridCells.Fixed(2),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    val images = state.catGallery.images
                    items(count = images.size) { index ->
                        val image = images[index]
                        Card(
                            modifier = Modifier
                                .size(cellSize)
                                .clickable {
                                    onImageClick(image.id)
                                },
                        ) {
                            PhotoPreview(
                                modifier = Modifier.fillMaxSize(),
                                url = image.url,
                                title = image.url,
                            )
                        }
                    }


                }
            }
        }

    }
}
