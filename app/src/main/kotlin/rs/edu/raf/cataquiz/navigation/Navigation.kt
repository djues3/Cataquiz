package rs.edu.raf.cataquiz.navigation

import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.runBlocking
import rs.edu.raf.cataquiz.catinfo.detail.catDetail
import rs.edu.raf.cataquiz.catinfo.list.catList
import rs.edu.raf.cataquiz.profile.ProfileStore
import rs.edu.raf.cataquiz.profile.create.createProfile
import rs.edu.raf.cataquiz.profile.details.profile
import rs.edu.raf.cataquiz.quiz.leaderboard.leaderboard
import rs.edu.raf.cataquiz.quiz.play.quiz
import rs.edu.raf.cataquiz.quiz.result.results

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
        startDestination = if (isEmpty) "createProfile" else "profile"
    ) {
        catList(
            "cats", onBreedClick = {
                navController.navigate(route = "cats/$it")
            }, navController = navController
        )
        catDetail("cats/{catId}", arguments = listOf(navArgument(name = "catId") {
            nullable = false
            type = NavType.StringType
        }), onClose = {
            navController.navigateUp()
        })
        createProfile("createProfile", onProfileCreated = {
            navController.navigate("cats") {
                popUpTo("createProfile") { inclusive = true }
            }
        })
        quiz(route = "quiz", onQuizFinished = {
            navController.navigate("results") {
                popUpTo("quiz") { inclusive = true }
            }
        }, onNavigateToResults = {
            navController.navigate("results") {
                popUpTo("quiz") { inclusive = true }
            }
        }, navController = navController)
        results(route = "results", navController = navController)
        leaderboard(route = "leaderboard", navController = navController)
        profile(route = "profile", navController = navController)
    }
}

inline val SavedStateHandle.catId: String
    get() = checkNotNull(get("catId")) { "catId is mandatory" }
/**
 * Stores the routes used by screens which have the application drawer.
 * */
sealed class Routes(val route: String) {
    data object CatList : Routes("cats")
    data object Quiz : Routes("quiz")
    data object Leaderboard : Routes("leaderboard")
    data object Profile : Routes("profile")
}

object NavigationActions {

    fun NavController.navigateToCatList() {
        navigate(Routes.CatList.route)
    }

    fun NavController.navigateToQuiz() {
        navigate(Routes.Quiz.route)
    }

    fun NavController.navigateToLeaderboard() {
        navigate(Routes.Leaderboard.route)
    }

    fun NavController.navigateToProfile() {
        navigate(Routes.Profile.route)
    }
}