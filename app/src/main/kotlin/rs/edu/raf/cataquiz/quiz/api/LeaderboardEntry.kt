package rs.edu.raf.cataquiz.quiz.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LeaderboardEntry(
    val category: Int,
    @SerialName("result") val score: Double,
    @SerialName("nickname") val username: String,
    val createdAt: Long,
)

@Serializable
data class QuizResultSend(
    val category: Int,
    @SerialName("result") val score: Double,
    @SerialName("nickname") val username: String,
)

@Serializable
data class QuizResultResponse(
    @SerialName("result") val entry: LeaderboardEntry,
    val ranking: Int,
)