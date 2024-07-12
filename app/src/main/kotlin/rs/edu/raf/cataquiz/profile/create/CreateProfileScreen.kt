package rs.edu.raf.cataquiz.profile.create

import CreateProfileContract.CreateProfileEvent
import CreateProfileContract.CreateProfileUiState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import isValid
import rs.edu.raf.cataquiz.profile.create.model.ProfileUiModel


fun NavGraphBuilder.createProfile(
    route: String,
    onProfileCreated: () -> Unit,
) = composable(
    route = route,
    enterTransition = { slideInHorizontally { it } },
    popExitTransition = { slideOutHorizontally { it } },
) { navBackStackEntry ->

    val createProfileViewModel = hiltViewModel<CreateProfileViewModel>(navBackStackEntry)

    val state = createProfileViewModel.state.collectAsState()

    CreateProfileScreen(
        state = state.value,
        eventPublisher = createProfileViewModel::setEvent,
        onProfileCreated = onProfileCreated,
    )
}

@Composable
fun CreateProfileScreen(
    state: CreateProfileUiState,
    eventPublisher: (CreateProfileEvent) -> Unit,
    onProfileCreated: () -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Create profile",
                modifier = Modifier.padding(vertical = 20.dp),
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = state.profile.fullName,
                onValueChange = {
                    eventPublisher(CreateProfileEvent.FullNameChanged(it))
                },
                label = {
                    Text(
                        text = "Full name",
                    )
                },
                modifier = Modifier.padding(paddingValues),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = state.profile.nickname,
                onValueChange = {
                    eventPublisher(CreateProfileEvent.UsernameChanged(it))
                },
                label = {
                    Text(
                        text = "Username",
                    )
                },
                modifier = Modifier.padding(paddingValues),
                singleLine = true,
                isError = state.isUsernameValid.not(),
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = state.profile.email,
                onValueChange = {
                    eventPublisher(CreateProfileEvent.EmailChanged(it))
                },
                label = {
                    Text(
                        text = "Email",
                    )
                },
                modifier = Modifier.padding(paddingValues),
                singleLine = true,
                isError = state.isEmailValid.not(),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    if (state.isValid()) {
                        eventPublisher(CreateProfileEvent.ProfileCreated(state.profile))
                        onProfileCreated()
                    }
                },
                modifier = Modifier.padding(paddingValues),
                content = {
                    Text(
                        text = "Create profile",
                    )
                },
                enabled = state.isValid() && state.profile != ProfileUiModel.EMPTY
            )


        }
    }
}