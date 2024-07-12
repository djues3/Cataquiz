package rs.edu.raf.cataquiz.quiz.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity
data class QuizResult(
    val username: String,
    val score: Double,
    val category: Int = 1,
    val timestamp: Long = Instant.now().toEpochMilli(),
    // for some reason, must be at the end...
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

) {
    companion object {
        val EMPTY = QuizResult("", 0.0)
    }
}