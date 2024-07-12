package rs.edu.raf.cataquiz.catinfo.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import rs.edu.raf.cataquiz.catinfo.list.CatListContract.BreedListEvent
import rs.edu.raf.cataquiz.catinfo.list.CatListContract.BreedListState
import rs.edu.raf.cataquiz.components.AppScaffold
import rs.edu.raf.cataquiz.ui.theme.EnableEdgeToEdge

fun NavGraphBuilder.catList(
    route: String,
    onBreedClick: (String) -> Unit,
    navController: NavHostController,
) = composable(route = route) { navBackStackEntry ->
    val breedListViewModel = hiltViewModel<CatListViewModel>(navBackStackEntry)
    val state = breedListViewModel.state.collectAsState()
    EnableEdgeToEdge()

    AppScaffold(
        title = "Learn about cats",
        profile = breedListViewModel.getProfile(),
        navController = navController
    ) {
        CatListScreen(
            state = state.value, eventPublisher = {
                breedListViewModel.setEvent(it)
            }, onBreedClick = onBreedClick
        )
    }

}


@Composable
fun CatListScreen(
    state: BreedListState,
    eventPublisher: (uiEvent: BreedListEvent) -> Unit,
    onBreedClick: (String) -> Unit,
) {
    if (state.loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Loading...",
                    modifier = Modifier.padding(vertical = 20.dp),
                )
                LinearProgressIndicator()
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchView(
                state = state, eventPublisher = eventPublisher
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = state.filteredBreeds, key = { it.id }) { breed ->
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .padding(16.dp)
                            .clickable {
                                onBreedClick(breed.id)
                            }
                            .fillMaxWidth()
                            .fillMaxHeight(),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = breed.name,
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            if (breed.altNames.isNotEmpty()) {
                                Text(
                                    text = breed.altNames.joinToString(", "),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = 8.dp),
                                )
                            }
                            Text(
                                text = if (breed.description.length > 250) {
                                    var i = 246
                                    while (breed.description[i].isLetter() || breed.description[i] == ' ' && i > 230) {
                                        i--
                                    }
                                    breed.description.substring(0, i) + "..."
                                } else {
                                    breed.description
                                },
                                style = MaterialTheme.typography.bodyMedium,
                            )
                            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                                breed.temperament.shuffled().take(3).forEach {
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
                        }
                    }

                }
            }
        }
    }

}

@Composable
fun SearchView(
    state: BreedListState,
    eventPublisher: (uiEvent: BreedListEvent) -> Unit,
) {
    val searchQuery = remember { mutableStateOf(state.query) }
    OutlinedTextField(value = searchQuery.value, onValueChange = {
        searchQuery.value = it
        eventPublisher(BreedListEvent.SearchQueryChanged(it))
    }, label = {
        Text(
            text = "Search",
        )
    }, modifier = Modifier.fillMaxWidth(), singleLine = true)
}