package rs.edu.raf.cataquiz.quiz.play

import android.util.Log
import rs.edu.raf.cataquiz.db.AppDatabase
import rs.edu.raf.cataquiz.quiz.db.QuizResult
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val database: AppDatabase,
) {
    suspend fun saveQuizResult(quizResult: QuizResult) {
        Log.d("QuizRepository", "Saving quiz result")
        Log.d("QuizRepository", "Result: $quizResult")
        database.quizResultDao().insertQuizResult(quizResult)
    }

}