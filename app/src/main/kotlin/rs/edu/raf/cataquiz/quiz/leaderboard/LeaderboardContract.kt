package rs.edu.raf.cataquiz.quiz.leaderboard

import rs.edu.raf.cataquiz.quiz.leaderboard.model.LeaderboardUiModel

class LeaderboardContract {
    data class LeaderboardState(
        val entries: List<LeaderboardUiModel> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String = "",
    )
}