package rs.edu.raf.cataquiz.catinfo.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.edu.raf.cataquiz.catinfo.db.cat.Cat
import rs.edu.raf.cataquiz.catinfo.list.CatListContract.*
import rs.edu.raf.cataquiz.catinfo.list.model.CatUiModel
import rs.edu.raf.cataquiz.catinfo.repository.CatRepository
import rs.edu.raf.cataquiz.profile.Profile
import rs.edu.raf.cataquiz.profile.ProfileStore
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class CatListViewModel @Inject constructor(
    val profileStore: ProfileStore,
    private val repository: CatRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(BreedListState())
    val state = _state.asStateFlow()
    private fun setState(reducer: BreedListState.() -> BreedListState) = _state.update(reducer)
    private val events = MutableSharedFlow<BreedListEvent>()
    fun setEvent(event: BreedListEvent) = viewModelScope.launch { events.emit(event) }

    init {
        updateCache()
        observeCats()
        observeEvents()
        observeQuery()
    }

    private fun observeCats() {
        viewModelScope.launch {
            repository.observeCats().distinctUntilChanged().collect { cats ->
                val catList = cats.map { it.asCatUiModel() }
                setState { copy(breeds = catList) }
                setState { copy(filteredBreeds = catList) }
            }
        }
    }

    private fun updateCache() {
        viewModelScope.launch {
            repository.updateCache()
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is BreedListEvent.SearchQueryChanged -> setState { copy(query = it.query) }
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeQuery() {
        viewModelScope.launch {
            events.filterIsInstance<BreedListEvent.SearchQueryChanged>()
                .debounce(500.0.milliseconds).collect {
                    val filtered = state.value.breeds.filter {
                        it.name.contains(
                            state.value.query, ignoreCase = true
                        ) || it.altNames.find { name ->
                            name.contains(
                                state.value.query, ignoreCase = true
                            )
                        } != null
                    }
                    setState { copy(filteredBreeds = filtered) }
                }
        }
    }

    fun getProfile(): Profile {
        return profileStore.getProfile()
    }

}

private fun Cat.asCatUiModel(): CatUiModel {
    return CatUiModel(
        id = this.id,
        name = this.name,
        altNames = this.altNames.split(","),
        description = this.description,
        temperament = this.temperament.split(","),
    )
}
