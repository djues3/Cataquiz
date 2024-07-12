package rs.edu.raf.cataquiz.profile.create

import CreateProfileContract.*
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.edu.raf.cataquiz.profile.Profile
import rs.edu.raf.cataquiz.profile.ProfileStore
import rs.edu.raf.cataquiz.profile.create.model.ProfileUiModel
import javax.inject.Inject

@HiltViewModel
class CreateProfileViewModel @Inject constructor(
    private val profileStore: ProfileStore,
) : ViewModel() {

    private val _state = MutableStateFlow(CreateProfileUiState())
    val state = _state.asStateFlow()
    private fun setState(reducer: CreateProfileUiState.() -> CreateProfileUiState) =
        _state.update(reducer)

    private val events = MutableSharedFlow<CreateProfileEvent>()
    fun setEvent(event: CreateProfileEvent) = viewModelScope.launch { events.emit(event) }


    init {
        observeProfile()
        observeEvents()
    }

    /**
     * Saves the profile on change.
     * */
    private fun observeProfile() {
        viewModelScope.launch {
            state.collect { profileUiState ->
                val profile = profileUiState.profile
                if (profile != ProfileUiModel.EMPTY) {
                    profileStore.updateProfile(profile.asProfile())
                }
            }
        }
    }

    /**
     * Handle incoming events.
     * */
    private fun observeEvents() {
        viewModelScope.launch {
            events.collect {
                when (it) {
                    is CreateProfileEvent.UsernameChanged -> {
                        val isValid = it.username.isValidUsername()
                        setState {
                            copy(
                                profile = profile.copy(nickname = it.username),
                                isUsernameValid = isValid,
                            )
                        }
                    }

                    is CreateProfileEvent.FullNameChanged -> {
                        setState { copy(profile = profile.copy(fullName = it.fullName)) }
                    }

                    is CreateProfileEvent.EmailChanged -> {
                        val isValid = it.email.isValidEmail()
                        setState {
                            copy(
                                profile = profile.copy(email = it.email),
                                isEmailValid = isValid,
                            )
                        }
                    }

                    is CreateProfileEvent.ProfileCreated -> {
                        setState { copy(profile = it.profile) }
                        profileStore.updateProfile(it.profile.asProfile())
                    }

                    else -> throw AssertionError("Unknown event $it")
                }
            }
        }
    }

}

private fun ProfileUiModel.asProfile(): Profile {
    return Profile(
        nickname = this.nickname,
        fullName = this.fullName,
        email = this.email,
    )
}

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidUsername(): Boolean {
    return this.length in 3..20 && this.matches(Regex("[a-zA-Z0-9_]+"))
}