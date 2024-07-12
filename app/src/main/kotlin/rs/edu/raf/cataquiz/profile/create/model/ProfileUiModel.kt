package rs.edu.raf.cataquiz.profile.create.model

data class ProfileUiModel(
    val nickname: String,
    val fullName: String,
    val email: String,
) {

    companion object {
        val EMPTY = ProfileUiModel(nickname = "", fullName = "", email = "")
    }
}