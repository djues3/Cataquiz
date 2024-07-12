package rs.edu.raf.cataquiz.catinfo.gallery

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.edu.raf.cataquiz.catinfo.db.cat.CatWithImages
import rs.edu.raf.cataquiz.catinfo.db.image.Image
import rs.edu.raf.cataquiz.catinfo.detail.model.ImageUIModel
import rs.edu.raf.cataquiz.catinfo.gallery.CatGalleryContract.CatGalleryUiState
import rs.edu.raf.cataquiz.catinfo.gallery.model.CatGalleryUiModel
import rs.edu.raf.cataquiz.catinfo.repository.CatRepository
import rs.edu.raf.cataquiz.navigation.catId
import javax.inject.Inject

@HiltViewModel
class CatGalleryViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val catRepository: CatRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CatGalleryUiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: CatGalleryUiState.() -> CatGalleryUiState) =
        _state.update(reducer)

    init {
        fetchCatGallery()
    }

    private fun fetchCatGallery() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(loading = true) }
            try {
                val cat = catRepository.getCatWithImages(catId = handle.catId)
                setState { copy(catGallery = cat.asCatGalleryUiModel()) }
            } catch (_: Exception) {
            } finally {
                setState { copy(loading = false) }
            }
        }
        observeCatGallery()
    }

    private fun observeCatGallery() {
        viewModelScope.launch(Dispatchers.IO) {
            catRepository.observeCatWithImage(catId = handle.catId).distinctUntilChanged().collect {
                setState { copy(catGallery = it.asCatGalleryUiModel()) }
            }
        }
    }

    private fun CatWithImages.asCatGalleryUiModel(): CatGalleryUiModel {
        return CatGalleryUiModel(catId = this.cat.id,
            images = this.images.map { it.asImageUiModel() })
    }

    private fun Image.asImageUiModel(): ImageUIModel {
        return ImageUIModel(
            id = this.id,
            url = this.url,
            width = this.width,
            height = this.height,
        )
    }
}