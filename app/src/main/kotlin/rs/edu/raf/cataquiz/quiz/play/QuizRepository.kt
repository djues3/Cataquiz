package rs.edu.raf.cataquiz.quiz.play

import android.util.Log
import rs.edu.raf.cataquiz.db.AppDatabase
import rs.edu.raf.cataquiz.quiz.api.LeaderboardApi
import rs.edu.raf.cataquiz.quiz.api.QuizResultResponse
import rs.edu.raf.cataquiz.quiz.api.QuizResultSend
import rs.edu.raf.cataquiz.quiz.db.QuizResult
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val database: AppDatabase,
    private val leaderboardApi: LeaderboardApi,
) {
    suspend fun saveQuizResult(quizResult: QuizResult) {
        Log.d("QuizRepository", "Saving quiz result")
        Log.d("QuizRepository", "Result: $quizResult")
        database.quizResultDao().insertQuizResult(quizResult)
    }

suspend fun sendQuizResult(quizResult: QuizResultSend): QuizResultResponse {
        Log.d("QuizRepository", "Sending quiz result")
        Log.d("QuizRepository", "Result: $quizResult")
        return leaderboardApi.sendQuizResult(quizResult)
    }

    fun getLatestQuizResult(): QuizResult {
        return database.quizResultDao().getLatestQuizResult()
    }
}