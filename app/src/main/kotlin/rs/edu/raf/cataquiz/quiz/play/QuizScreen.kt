package rs.edu.raf.cataquiz.quiz.play

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.SubcomposeAsyncImage
import rs.edu.raf.cataquiz.quiz.play.QuizContract.QuizEvent.TimeUp
import rs.edu.raf.cataquiz.quiz.model.questionText
import rs.edu.raf.cataquiz.ui.theme.EnableEdgeToEdge

fun NavGraphBuilder.quiz(
    route: String,
    onQuizFinished: () -> Unit,
    onNavigateToResults: () -> Unit,
    navController: NavController,
) = composable(route) {
    val viewModel = hiltViewModel<QuizViewModel>()
    val state = viewModel.state.collectAsState()
    EnableEdgeToEdge()

    QuizScreen(
        state = state,
        onNavigateToResults = onNavigateToResults,
        onQuizFinished = onQuizFinished,
        navController = navController,
        viewModel = viewModel
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    state: State<QuizContract.QuizState>,
    onNavigateToResults: () -> Unit,
    onQuizFinished: () -> Unit,
    navController: NavController,
    viewModel: QuizViewModel,
) {
    var showQuitDialog by remember { mutableStateOf(false) }
    if (state.value.isLoading) {
        return
    }
    if (state.value.currentQuestionIndex >= 20) {
        onQuizFinished()
        return
    }

    val currentQuestion = state.value.questions[state.value.currentQuestionIndex]
    BackHandler(enabled = true) {
        showQuitDialog = true
    }


    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Guess The Cat") })
    }) { paddingValues ->

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                val timeLeft = state.value.timeLeft

                if (timeLeft <= 0) {
                    viewModel.setEvent(TimeUp)
                    navController.navigate("cats") {
                        popUpTo("quiz") { inclusive = true }
                    }
                }

                val color by animateColorAsState(
                    if (timeLeft > 20.0f) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    label = "Color-change"
                )

                LinearProgressIndicator(
                    progress = { timeLeft.toFloat() / MaxTime },
                    color = color,
                    modifier = Modifier.fillMaxWidth(),
                )

                Text(
                    text = currentQuestion.questionText, modifier = Modifier.padding(16.dp)
                )


                SubcomposeAsyncImage(
                    model = currentQuestion.imageUrl,
                    contentDescription = "Question Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(300.dp)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), modifier = Modifier.weight(1f)
                ) {
                    items(currentQuestion.options.size) { index ->
                        Button(
                            onClick = {
                                viewModel.setEvent(
                                    QuizContract.QuizEvent.QuestionAnswered(
                                        currentQuestion.options[index],
                                        currentQuestion,
                                    )
                                )
                            }, modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(text = currentQuestion.options[index])
                        }
                    }
                }

                Button(
                    onClick = { showQuitDialog = true },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp)
                ) {
                    Text("Quit Quiz")
                }
            }
            if (showQuitDialog) {
                AlertDialog(onDismissRequest = { showQuitDialog = false },
                    title = { Text("Quit Quiz") },
                    text = { Text("Are you sure you want to quit the quiz?") },
                    confirmButton = {
                        Button(onClick = {
                            showQuitDialog = false
                            viewModel.setEvent(QuizContract.QuizEvent.QuizFinished)
                            onNavigateToResults()
                        }) {
                            Text("Yes")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showQuitDialog = false }) {
                            Text("No")
                        }
                    })
            }
        }
    }
}
