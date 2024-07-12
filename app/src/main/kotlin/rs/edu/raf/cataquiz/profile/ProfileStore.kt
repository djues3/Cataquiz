package rs.edu.raf.cataquiz.profile

import androidx.datastore.core.DataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileStore @Inject constructor(
    private val persistence: DataStore<Profile>,
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val data = persistence.data.stateIn(
            scope = scope,
            started = SharingStarted.Eagerly,
            initialValue = runBlocking { persistence.data.first() },
        )


    suspend fun updateProfile(profile: Profile) {
        persistence.updateData { profile }
    }

    fun getProfile(): Profile {
        return data.value
    }

    fun isEmpty(): Boolean {
        return data.value == Profile.EMPTY
    }
}