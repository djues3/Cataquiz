package rs.edu.raf.cataquiz.quiz.model

data class QuizUiModel(
    val questions: List<Question>,
    val currentQuestionIndex: Int,
    val correctAnswers: Int,
    val timeLeft: Long,
)
