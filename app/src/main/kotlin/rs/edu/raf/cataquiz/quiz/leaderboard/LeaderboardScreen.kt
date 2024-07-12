package rs.edu.raf.cataquiz.quiz.leaderboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import rs.edu.raf.cataquiz.components.AppScaffold
import rs.edu.raf.cataquiz.quiz.leaderboard.LeaderboardContract.LeaderboardState

fun NavGraphBuilder.leaderboard(route: String, navController: NavHostController) =
    composable(route) {
        val viewModel = hiltViewModel<LeaderboardViewModel>()
        val state = viewModel.state.collectAsState()
        LeaderboardScreen(navController = navController, viewModel = viewModel, state = state.value)
    }

@Composable
fun LeaderboardScreen(
    navController: NavHostController,
    viewModel: LeaderboardViewModel,
    state: LeaderboardState,
) {
    AppScaffold(
        navController = navController,
        title = "Leaderboard",
        profile = viewModel.getProfile(),
    ) {
        if (!state.isLoading) {
            val entries = state.entries
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(entries.size) { index ->
                    val uiModel = entries[index]
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Username: ${uiModel.username}")
                        Text("Total Games: ${uiModel.totalGames}")
                        Text("Category: ${uiModel.category}")
                        Text("Score: ${uiModel.score}")
                    }
                    HorizontalDivider()
                }
            }
        }
    }
}
