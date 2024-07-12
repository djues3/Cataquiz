package rs.edu.raf.cataquiz.quiz.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LeaderboardApi {
    @GET("leadboard?category=1")
    suspend fun getLeaderboard(): List<LeaderboardEntry>

    @POST("leaderboard")
    suspend fun sendQuizResult(@Body quizResult: QuizResultSend): QuizResultResponse

}