package rs.edu.raf.cataquiz.quiz.model

data class Question(
    val questionType: QuestionType,
    val imageUrl: String,
    val correctAnswer: String,
    val options: List<String>,
)


enum class QuestionType {
    BREED_IDENTIFICATION,
    TEMPERAMENT_INTRUDER,
    TEMPERAMENT_MATCH,
}

inline val Question.questionText: String
    get() = when (questionType) {
        QuestionType.BREED_IDENTIFICATION -> "What breed is this?"
        QuestionType.TEMPERAMENT_INTRUDER -> "Remove the intruder"
        QuestionType.TEMPERAMENT_MATCH -> "Match the temperament"
    }

