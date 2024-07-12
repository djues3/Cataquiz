package rs.edu.raf.cataquiz.profile.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.edu.raf.cataquiz.profile.Profile
import rs.edu.raf.cataquiz.profile.ProfileStore
import rs.edu.raf.cataquiz.profile.details.ProfileDetailsContract.ProfileDetailsState
import rs.edu.raf.cataquiz.profile.details.model.ProfileDetailsUiModel
import rs.edu.raf.cataquiz.profile.details.model.QuizResultUiModel
import rs.edu.raf.cataquiz.quiz.QuizRepository
import rs.edu.raf.cataquiz.quiz.api.LeaderboardEntry
import rs.edu.raf.cataquiz.quiz.db.QuizResult
import rs.edu.raf.cataquiz.quiz.leaderboard.categoryToString
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ProfileDetailsViewModel @Inject constructor(
    private val profileStore: ProfileStore,
    private val quizRepository: QuizRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileDetailsState())
    val state = _state.asStateFlow()
    private fun setState(reducer: ProfileDetailsState.() -> ProfileDetailsState) =
        _state.update(reducer)

    init {
        fetchResults()
    }


    private fun fetchResults() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(isLoading = true) }
            val profile = getProfile()
            val results = quizRepository.getResults(profile.nickname)
            val globalPb = quizRepository.getGlobalPbForUser(profile.nickname)
            val localPb = quizRepository.getLocalPbForUser(profile.nickname)
            setState {
                copy(
                    profile = profile.asProfileDetailsUiModel(results),
                    isLoading = false,
                    localPb = localPb?.asQuizResultUiModel() ?: QuizResultUiModel.EMPTY,
                    leaderboardPb = globalPb?.asQuizResultUiModel() ?: QuizResultUiModel.EMPTY
                )
            }
        }
    }


    fun getProfile(): Profile {
        return profileStore.getProfile()
    }

}

private fun LeaderboardEntry?.asQuizResultUiModel(): QuizResultUiModel? {
    return this?.let {
        QuizResultUiModel(
            category = categoryToString(it.category),
            score = it.score,
            username = it.username,
            timestamp = Instant.ofEpochMilli(it.createdAt).atZone(ZoneId.systemDefault())
                .toLocalDateTime().toString(),
        )
    }
}

private fun Profile.asProfileDetailsUiModel(results: List<QuizResult>): ProfileDetailsUiModel {
    return ProfileDetailsUiModel(
        nickname = this.nickname,
        fullName = this.fullName,
        email = this.email,
        results = results.map { it.asQuizResultUiModel() },
    )
}

private fun QuizResult.asQuizResultUiModel(): QuizResultUiModel {
    val createdAt = Instant.ofEpochMilli(this.timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime().toString()
    return QuizResultUiModel(
        category = categoryToString(category), score = score, username = username,
        timestamp = createdAt,
    )
}
