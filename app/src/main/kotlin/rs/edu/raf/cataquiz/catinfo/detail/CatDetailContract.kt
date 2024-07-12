package rs.edu.raf.cataquiz.catinfo.detail

import rs.edu.raf.cataquiz.catinfo.detail.model.CatDetailUiModel

interface CatDetailContract {
    data class CatDetailUiState(
        val loading: Boolean = false,
        val breedId: String,
        val data: CatDetailUiModel? = null,
    )
}