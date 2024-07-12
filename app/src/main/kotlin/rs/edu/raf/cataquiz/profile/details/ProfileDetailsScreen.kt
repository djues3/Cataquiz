package rs.edu.raf.cataquiz.profile.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import rs.edu.raf.cataquiz.components.AppScaffold
import rs.edu.raf.cataquiz.profile.details.ProfileDetailsContract.ProfileDetailsState
import rs.edu.raf.cataquiz.profile.details.model.QuizResultUiModel
import rs.edu.raf.cataquiz.ui.theme.EnableEdgeToEdge

fun NavGraphBuilder.profile(route: String, navController: NavHostController) = composable(route) {
    val viewModel = hiltViewModel<ProfileDetailsViewModel>()
    val state = viewModel.state.collectAsState()
    EnableEdgeToEdge()

    AppScaffold(title = "Profile",
        profile = viewModel.getProfile(),
        navController = navController,
        actions = {
            IconButton(onClick = { navController.navigate("createProfile") }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
        }) {

        ProfileDetailsScreen(
            state = state.value
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDetailsScreen(
    state: ProfileDetailsState,
) {
    if (state.isLoading) {
        Column {
            Text("Loading...")
            CircularProgressIndicator()
        }
    } else {
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = "Profile") })
        }) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Column(modifier = Modifier.padding(15.dp)) {
                    OutlinedTextField(
                        value = "Nickname: ${state.profile.nickname}",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = "Full Name: ${state.profile.fullName}",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = "Email: ${state.profile.email}",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    Text(text = "Quiz Results:")
                }
                HorizontalDivider()
                Text("Personal Best:")
                QuizResultItem(state.localPb)
                HorizontalDivider()
                Text("Leaderboard Best:")
                QuizResultItem(state.leaderboardPb)
                HorizontalDivider(
                    modifier = Modifier
                        .height(4.dp)
                        .padding(top = 8.dp)
                )
                val entries = state.profile.results
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(entries.size) { index ->
                        val uiModel = entries[index]
                        QuizResultItem(uiModel)
                        HorizontalDivider()
                    }
                }

            }
        }
    }
}

@Composable
fun QuizResultItem(result: QuizResultUiModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row {
            Text("Category: ${result.category}")

            Text("Timestamp: ${result.timestamp}", modifier = Modifier.padding(start = 8.dp))
        }
        Text("Score: ${result.score}")
        Text("Username: ${result.username}")
    }
}