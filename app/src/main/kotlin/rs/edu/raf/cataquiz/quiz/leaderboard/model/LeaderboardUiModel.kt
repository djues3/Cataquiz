package rs.edu.raf.cataquiz.quiz.leaderboard.model

data class LeaderboardUiModel(
    val category: String,
    val username: String,
    val score: Double,
    val totalGames: Int,
)
