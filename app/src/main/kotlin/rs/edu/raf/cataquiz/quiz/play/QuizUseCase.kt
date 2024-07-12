package rs.edu.raf.cataquiz.quiz.play

import rs.edu.raf.cataquiz.quiz.model.Question
import rs.edu.raf.cataquiz.quiz.model.QuestionType
import javax.inject.Inject

class QuizUseCase @Inject constructor(
    private val questionGenerator: QuestionGenerator,
) {
    suspend fun startQuiz(): List<Question> {
        return List(20) {
            questionGenerator.generateQuestion(QuestionType.entries.random())
        }
    }
}