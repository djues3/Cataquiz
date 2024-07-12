package rs.edu.raf.cataquiz.catinfo.list

import rs.edu.raf.cataquiz.catinfo.list.model.CatUiModel

interface CatListContract {
    data class BreedListState(
        val loading: Boolean = false,
        val breeds: List<CatUiModel> = emptyList(),
        val query: String = "",
        var filteredBreeds: List<CatUiModel> = emptyList(),
    )
    sealed class BreedListEvent {
        data class SearchQueryChanged(val query: String) : BreedListEvent()
    }
}