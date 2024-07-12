package rs.edu.raf.cataquiz.quiz.play

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.edu.raf.cataquiz.profile.ProfileStore
import rs.edu.raf.cataquiz.quiz.play.QuizContract.QuizEvent
import rs.edu.raf.cataquiz.quiz.play.QuizContract.QuizEvent.*

import rs.edu.raf.cataquiz.quiz.play.QuizContract.QuizState
import rs.edu.raf.cataquiz.quiz.db.QuizResult
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizUseCase: QuizUseCase,
    private val quizRepository: QuizRepository,
    private val profileStore: ProfileStore,
) : ViewModel() {
    // TODO: Change this to 300
    private val _state = MutableStateFlow(QuizState(timeLeft = 5))
    val state = _state.asStateFlow()

    private fun setState(reducer: QuizState.() -> QuizState) = _state.update(reducer)
    private val events = MutableSharedFlow<QuizEvent>()
    fun setEvent(event: QuizEvent) = viewModelScope.launch { events.emit(event) }

    private var timerJob: Job? = null

    init {
        // perhaps this should be called from the UI.
        startQuiz()
        observeEvents()
    }


    private fun startQuiz() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val questions = quizUseCase.startQuiz()
            setState { copy(questions = questions, isLoading = false) }
            startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_state.value.timeLeft > 0) {
                delay(1000)
                setState { copy(timeLeft = timeLeft - 1) }
            }
        }
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is StartQuiz -> {
                        startQuiz()
                    }
                    is QuestionAnswered -> {
                        val isCorrect = it.answer == it.question.correctAnswer
                        setState {
                            copy(
                                correctAnswers = correctAnswers + if (isCorrect) 1 else 0,
                                currentQuestionIndex = currentQuestionIndex + 1
                            )
                        }
                        if (state.value.currentQuestionIndex >= 20) {
                            setEvent(QuizFinished)
                        }
                    }

                    is TimeUp -> {
                        setState { copy(isFinished = true)}
                        finishQuiz()
                    }
                    is QuizFinished -> {
                        setState { copy(isFinished = true) }
                        finishQuiz()
                    }
                }
            }
        }
    }

    /**
     * Navigation to the next page (the results page, must be handled by the UI).
     * */
    private fun finishQuiz() {
        val points = calculatePoints(state.value.correctAnswers, state.value.timeLeft)
        val quizResult = QuizResult(username = profileStore.getProfile().nickname, score = points)
        viewModelScope.launch {
            quizRepository.saveQuizResult(quizResult)
        }

    }

    private fun calculatePoints(correctAnswers: Int, timeLeft: Long): Double {
        return (correctAnswers * 2.5 * (1 + (timeLeft + 120) / MaxTime)).coerceAtMost(100.00)
    }
}