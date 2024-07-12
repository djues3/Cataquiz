package rs.edu.raf.cataquiz.catinfo.gallery.model

import rs.edu.raf.cataquiz.catinfo.detail.model.ImageUIModel

data class CatGalleryUiModel(
    val catId: String,
    val images: List<ImageUIModel> = emptyList(),
) {
    companion object {
        val EMPTY: CatGalleryUiModel = CatGalleryUiModel(catId = "")
    }
}
