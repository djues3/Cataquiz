package rs.edu.raf.cataquiz.profile

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val nickname: String,
    val fullName: String,
    val email: String
) {
    companion object {
        val EMPTY = Profile(nickname = "", fullName = "", email = "")
    }
}
