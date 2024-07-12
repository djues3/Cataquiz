package rs.edu.raf.cataquiz.quiz.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.edu.raf.cataquiz.profile.Profile
import rs.edu.raf.cataquiz.profile.ProfileStore
import rs.edu.raf.cataquiz.quiz.api.LeaderboardApi
import rs.edu.raf.cataquiz.quiz.api.LeaderboardEntry
import rs.edu.raf.cataquiz.quiz.leaderboard.LeaderboardContract.LeaderboardState
import rs.edu.raf.cataquiz.quiz.leaderboard.model.LeaderboardUiModel
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val leaderboardApi: LeaderboardApi,
    private val profileStore: ProfileStore,
) : ViewModel() {
    private val _state = MutableStateFlow(LeaderboardState())
    val state = _state.asStateFlow()
    private fun setState(reducer: LeaderboardState.() -> LeaderboardState) = _state.update(reducer)

    init {
        fetchLeaderboard()
    }

    private fun fetchLeaderboard() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val entries = leaderboardApi.getLeaderboard()
            val entriesByUsername = entries.groupBy { it.username }
            setState {
                copy(
                    entries = entries.map { it.asLeaderboardUiModel(entriesByUsername) },
                    isLoading = false
                )
            }
        }
    }

    fun getProfile(): Profile {
        return profileStore.getProfile()
    }
}

private fun LeaderboardEntry.asLeaderboardUiModel(entriesByUsername: Map<String, List<LeaderboardEntry>>): LeaderboardUiModel {
    val totalGames = entriesByUsername[username]?.size ?: 0
    return LeaderboardUiModel(
        category = categoryToString(category),
        username = username,
        score = score,
        totalGames = totalGames,
    )
}

fun categoryToString(category: Int): String {
    return when (category) {
        1 -> "Guess the Cat"
        else -> "Unknown"
    }
}