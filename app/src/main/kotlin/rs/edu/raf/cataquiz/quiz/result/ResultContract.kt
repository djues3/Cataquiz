package rs.edu.raf.cataquiz.quiz.result

import rs.edu.raf.cataquiz.quiz.db.QuizResult

interface ResultContract {
    data class ResultState(
        val quizResult: QuizResult = QuizResult.EMPTY,
        val isLoading: Boolean = false,
        val sendResult: Boolean = false,
        val isError: Boolean = false,
        val errorMessage: String = "",
    )

    sealed class ResultEvent {
        data object SendResult : ResultEvent()
    }
}