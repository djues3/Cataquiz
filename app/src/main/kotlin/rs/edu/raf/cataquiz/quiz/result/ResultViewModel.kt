package rs.edu.raf.cataquiz.quiz.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.edu.raf.cataquiz.profile.Profile
import rs.edu.raf.cataquiz.profile.ProfileStore
import rs.edu.raf.cataquiz.quiz.api.QuizResultSend
import rs.edu.raf.cataquiz.quiz.db.QuizResult
import rs.edu.raf.cataquiz.quiz.QuizRepository
import rs.edu.raf.cataquiz.quiz.result.ResultContract.ResultEvent
import rs.edu.raf.cataquiz.quiz.result.ResultContract.ResultEvent.SendResult
import rs.edu.raf.cataquiz.quiz.result.ResultContract.ResultState
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val profileStore: ProfileStore,
) : ViewModel() {
    private val _state = MutableStateFlow(ResultState())
    val state = _state.asStateFlow()
    fun setState(reducer: ResultState.() -> ResultState) = _state.update(reducer)
    private val events = MutableSharedFlow<ResultEvent>()
    fun setEvent(event: ResultEvent) = viewModelScope.launch { events.emit(event) }

    init {
        observeEvents()
        fetchLatestQuizResult()
    }

    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is SendResult -> {
                        sendResult()
                    }
                }
            }
        }
    }

    private fun fetchLatestQuizResult() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(isLoading = true) }
            val quizResult = quizRepository.getLatestQuizResult()
            setState { copy(quizResult = quizResult, isLoading = false) }
        }
    }

    private fun sendResult() {
        viewModelScope.launch {
            setState { copy(isError = false, errorMessage = "") }
            val quizResult = state.value.quizResult
            try {
                quizRepository.sendQuizResult(quizResult.asSendQuizResult())
            } catch (e: Exception) {
                setState { copy(isError = true, errorMessage = e.message ?: "Unknown error") }
            }
        }
    }

    fun getProfile(): Profile {
        return profileStore.getProfile()
    }
}

private fun QuizResult.asSendQuizResult(): QuizResultSend {
    return QuizResultSend(
        category = category,
        score = score,
        username = username,
    )
}
