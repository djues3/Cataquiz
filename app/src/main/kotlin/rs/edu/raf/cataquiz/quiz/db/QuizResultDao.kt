package rs.edu.raf.cataquiz.quiz.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface QuizResultDao {

    @Upsert
    suspend fun upsertQuizResult(quizResult: QuizResult)

    @Insert
    suspend fun insertQuizResult(quizResult: QuizResult)


    @Query("SELECT * FROM QuizResult")
    fun observeAllQuizResults(): Flow<List<QuizResult>>
}