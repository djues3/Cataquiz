package rs.edu.raf.cataquiz.profile.details.model

import java.time.LocalDateTime

data class ProfileDetailsUiModel(
    val nickname: String,
    val fullName: String,
    val email: String,
    val results: List<QuizResultUiModel>,
) {
    companion object {
        val EMPTY: ProfileDetailsUiModel = ProfileDetailsUiModel(
            nickname = "",
            fullName = "",
            email = "",
            results = emptyList(),
        )
    }
}

data class QuizResultUiModel(
    val category: String,
    val score: Double,
    val username: String,
    val timestamp: String,
) {
    companion object {
        val EMPTY: QuizResultUiModel
            get() = QuizResultUiModel("", 0.0, "", LocalDateTime.now().toString())
    }
}