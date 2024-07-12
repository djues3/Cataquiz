package rs.edu.raf.cataquiz.navigation

import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.runBlocking
import rs.edu.raf.cataquiz.catinfo.detail.catDetail
import rs.edu.raf.cataquiz.catinfo.list.catList
import rs.edu.raf.cataquiz.profile.ProfileStore
import rs.edu.raf.cataquiz.profile.create.createProfile

@Composable
fun CataquizNavigation(
    profileStore: ProfileStore,
) {
    val navController = rememberNavController()
    val isEmpty = runBlocking { profileStore.isEmpty() }
    NavHost(
        navController = navController,
        enterTransition = {
            slideInHorizontally(
                animationSpec = spring(),
                initialOffsetX = { it },
            )
        },
        exitTransition = { scaleOut(targetScale = 0.75f) },
        popEnterTransition = { scaleIn(initialScale = 0.75f) },
        popExitTransition = { slideOutHorizontally { it } },
        startDestination = if (isEmpty) "createProfile" else "cats"
    ) {
        catList("cats", onBreedClick = {
            navController.navigate(route = "cats/$it")
        })
        catDetail("cats/{catId}", arguments = listOf(
            navArgument(name = "catId") {
                nullable = false
                type = NavType.StringType
            }
        ), onClose = {
            navController.navigateUp()
        })
        createProfile("createProfile", onProfileCreated = {
            navController.navigate("start") {
                popUpTo("createProfile") { inclusive = true }
            }
        })
    }
}

inline val SavedStateHandle.catId: String
    get() = checkNotNull(get("catId")) { "catId is mandatory" }