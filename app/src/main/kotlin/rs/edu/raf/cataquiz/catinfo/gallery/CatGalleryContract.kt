package rs.edu.raf.cataquiz.catinfo.gallery

import rs.edu.raf.cataquiz.catinfo.gallery.model.CatGalleryUiModel

interface CatGalleryContract {
    data class CatGalleryUiState(
        val loading: Boolean = false,
        val catGallery: CatGalleryUiModel = CatGalleryUiModel.EMPTY,    )
}