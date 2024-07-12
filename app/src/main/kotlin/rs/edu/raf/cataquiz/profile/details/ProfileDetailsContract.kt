package rs.edu.raf.cataquiz.profile.details

import rs.edu.raf.cataquiz.profile.details.model.ProfileDetailsUiModel
import rs.edu.raf.cataquiz.profile.details.model.QuizResultUiModel

interface ProfileDetailsContract {
    data class ProfileDetailsState(
        val isLoading: Boolean = false,
        val profile: ProfileDetailsUiModel = ProfileDetailsUiModel.EMPTY,
        val localPb: QuizResultUiModel = QuizResultUiModel.EMPTY,
        val leaderboardPb: QuizResultUiModel = QuizResultUiModel.EMPTY,
        )

}