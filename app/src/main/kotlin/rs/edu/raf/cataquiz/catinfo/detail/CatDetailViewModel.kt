package rs.edu.raf.cataquiz.catinfo.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import rs.edu.raf.cataquiz.catinfo.db.cat.Cat
import rs.edu.raf.cataquiz.catinfo.db.image.Image
import rs.edu.raf.cataquiz.catinfo.detail.CatDetailContract.*
import rs.edu.raf.cataquiz.catinfo.detail.model.CatDetailUiModel
import rs.edu.raf.cataquiz.catinfo.detail.model.ImageUIModel
import rs.edu.raf.cataquiz.catinfo.repository.CatRepository
import rs.edu.raf.cataquiz.catinfo.repository.ImageRepository
import rs.edu.raf.cataquiz.navigation.catId
import javax.inject.Inject


@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val catRepository: CatRepository,
    private val imageRepository: ImageRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CatDetailUiState(breedId = handle.catId))
    val state = _state.asStateFlow()
    private fun setState(reducer: CatDetailUiState.() -> CatDetailUiState) =
        _state.getAndUpdate(reducer)

    init {
        fetchCat()
    }

    private fun fetchCat() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                catRepository.fetchCat(catId = handle.catId)
                imageRepository.fetchCatImages(catId = handle.catId)
            } catch (_: Exception) {
            } finally {
                setState { copy(loading = false) }
            }
        }
        observeCat()
    }

    private fun observeCat() {
        viewModelScope.launch {
            setState { copy(loading = true) }
            try {
                val cat = catRepository.getCat(catId = handle.catId)
                val c = cat.asCatDetailUiModel()
                setState { copy(data = c) }
            } catch (_: Exception) {
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private suspend fun Cat.asCatDetailUiModel(): CatDetailUiModel {
        val images = imageRepository.getImages(catId = this.id)
        return CatDetailUiModel(id = this.id,
            name = this.name,
            description = this.description,
            temperament = this.temperament.split(","),
            countriesOfOrigin = this.countryCodes.split(" "),
            lifeSpan = this.lifeSpan,
            averageWeight = this.weight.metric,
            adaptability = this.adaptability,
            affectionLevel = this.affectionLevel,
            childFriendly = this.childFriendly,
            dogFriendly = this.dogFriendly,
            energyLevel = this.energyLevel,
            grooming = this.grooming,
            healthIssues = this.healthIssues,
            intelligence = this.intelligence,
            sheddingLevel = this.sheddingLevel,
            socialNeeds = this.socialNeeds,
            strangerFriendly = this.strangerFriendly,
            vocalisation = this.vocalisation,
            isRare = this.rare == 1,
            wikipediaUrl = this.wikipediaUrl,
            images = images.map { it.asImageUiModel() })
    }
}

private fun Image.asImageUiModel(): ImageUIModel {
    return ImageUIModel(
        id = this.id,
        url = this.url,
        width = this.width,
        height = this.height,
    )
}

