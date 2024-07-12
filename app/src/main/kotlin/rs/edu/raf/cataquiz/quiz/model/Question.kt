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
        QuestionType.BREED_IDENTIFICATION -> "What is the breed of this cat?"
        QuestionType.TEMPERAMENT_INTRUDER -> "What is the temperament of this cat?"
        QuestionType.TEMPERAMENT_MATCH -> "What is the temperament of this cat?"
    }

