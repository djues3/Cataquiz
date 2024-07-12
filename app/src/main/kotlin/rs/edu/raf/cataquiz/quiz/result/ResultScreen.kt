package rs.edu.raf.cataquiz.quiz.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import rs.edu.raf.cataquiz.components.AppScaffold
import rs.edu.raf.cataquiz.navigation.NavigationActions.navigateToCatList
import rs.edu.raf.cataquiz.quiz.result.ResultContract.ResultEvent.SendResult
import rs.edu.raf.cataquiz.quiz.result.ResultContract.ResultState

fun NavGraphBuilder.results(route: String, navController: NavHostController) = composable(route) {
    val viewModel = hiltViewModel<ResultViewModel>()
    val state = viewModel.state.collectAsState()
    ResultScreen(navController = navController, viewModel = viewModel, state = state.value)
}

@Composable
fun ResultScreen(navController: NavHostController, viewModel: ResultViewModel, state: ResultState) {
    AppScaffold(
        navController = navController,
        title = "Result",
        profile = viewModel.getProfile(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your Score", fontSize = 24.sp, fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "${state.quizResult.score} / 100",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))
            if (state.isError) {
                val text = state.errorMessage.ifEmpty { "Unknown error occurred" }
                Text(text = text, color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(16.dp))
            }

            Button(onClick = {
                viewModel.setEvent(SendResult)
                navController.navigate("cats")
            }) {
                Text("Submit Score to Leaderboard")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigateToCatList()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),

                ) {
                Text("Go back...")
            }
        }
    }
}
