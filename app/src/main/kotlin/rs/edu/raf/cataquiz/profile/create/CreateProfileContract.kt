import rs.edu.raf.cataquiz.profile.create.model.ProfileUiModel


interface CreateProfileContract {
    data class CreateProfileUiState(
        val profile: ProfileUiModel = ProfileUiModel.EMPTY,
        val isEmailValid: Boolean = true,
        val isUsernameValid: Boolean = true,
    )

    sealed class CreateProfileEvent {
        data class UsernameChanged(val username: String) : CreateProfileEvent()
        data class FullNameChanged(val fullName: String) : CreateProfileEvent()
        data class EmailChanged(val email: String) : CreateProfileEvent()
        data class ProfileCreated(val profile: ProfileUiModel) : CreateProfileEvent()
    }
}

fun CreateProfileContract.CreateProfileUiState.isValid(): Boolean {
    return isUsernameValid && isEmailValid
}