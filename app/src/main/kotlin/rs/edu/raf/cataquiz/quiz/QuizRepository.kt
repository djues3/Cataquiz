package rs.edu.raf.cataquiz.quiz

import rs.edu.raf.cataquiz.db.AppDatabase
import rs.edu.raf.cataquiz.quiz.api.LeaderboardApi
import rs.edu.raf.cataquiz.quiz.api.LeaderboardEntry
import rs.edu.raf.cataquiz.quiz.api.QuizResultResponse
import rs.edu.raf.cataquiz.quiz.api.QuizResultSend
import rs.edu.raf.cataquiz.quiz.db.QuizResult
import javax.inject.Inject

class QuizRepository @Inject constructor(
    private val database: AppDatabase,
    private val leaderboardApi: LeaderboardApi,
) {
    suspend fun saveQuizResult(quizResult: QuizResult) {
        database.quizResultDao().insertQuizResult(quizResult)
    }

    suspend fun sendQuizResult(quizResult: QuizResultSend): QuizResultResponse {
        return leaderboardApi.sendQuizResult(quizResult)
    }

    fun getLatestQuizResult(): QuizResult {
        return database.quizResultDao().getLatestQuizResult()
    }

    fun getResults(username: String): List<QuizResult> {
        return database.quizResultDao().getResults(username)
    }

    suspend fun getGlobalPbForUser(username: String): LeaderboardEntry? {
        return leaderboardApi.getLeaderboard().find { it.username == username }
    }

    fun getLocalPbForUser(username: String): QuizResult? {
        return database.quizResultDao().getResults(username).maxByOrNull { it.score }
    }

}