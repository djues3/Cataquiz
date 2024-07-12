package rs.edu.raf.cataquiz.quiz

import rs.edu.raf.cataquiz.quiz.model.Question

interface QuizContract {
    data class QuizState(
        val questions: List<Question> = emptyList(),
        val currentQuestionIndex: Int = 0,
        val correctAnswers: Int = 0,
        val timeLeft: Long = MaxTime,
        val isLoading: Boolean = false,
        val isFinished: Boolean = false,
    )

    sealed class QuizEvent {
        data object StartQuiz : QuizEvent()
        data class QuestionAnswered(val answer: String, val question: Question) : QuizEvent()
        data object TimeUp : QuizEvent()
        data object QuizFinished : QuizEvent()
    }
}


const val MaxTime = 300L