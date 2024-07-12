package rs.edu.raf.cataquiz.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import rs.edu.raf.cataquiz.navigation.NavigationActions.navigateToCatList
import rs.edu.raf.cataquiz.navigation.NavigationActions.navigateToLeaderboard
import rs.edu.raf.cataquiz.navigation.NavigationActions.navigateToProfile
import rs.edu.raf.cataquiz.navigation.NavigationActions.navigateToQuiz
import rs.edu.raf.cataquiz.profile.Profile
import rs.edu.raf.cataquiz.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String,
    profile: Profile,
    navController: NavHostController,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable () -> Unit,
) {
    val uiScope = rememberCoroutineScope()
    val drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed)

    BackHandler(enabled = drawerState.isOpen) {
        uiScope.launch { drawerState.close() }
    }
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        DrawerContent(
            profile = profile,
            onBreedClick = {
                navController.navigateToCatList()
            },
            onProfileClick = {
                navController.navigateToProfile()
            },
            onQuizClick = {
                navController.navigateToQuiz()
            },
            onLeaderboardClick = {
                navController.navigateToLeaderboard()
            },
        )
    }) {
        Scaffold(topBar = {
            TopAppBar(title = { Text(text = title) }, navigationIcon = {
                IconButton(onClick = {
                    uiScope.launch { drawerState.open() }
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Open drawer")
                }
            }, actions = actions)
        }) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                content()
            }
        }
    }
}

@Composable
fun DrawerContent(
    profile: Profile,
    onBreedClick: () -> Unit,
    onProfileClick: () -> Unit,
    onQuizClick: () -> Unit,
    onLeaderboardClick: () -> Unit,
) {
    BoxWithConstraints {
        ModalDrawerSheet(modifier = Modifier.width(maxWidth * 1 / 2)) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.BottomStart,
                ) {
                    Text(
                        modifier = Modifier.padding(all = 16.dp),
                        text = profile.nickname,
                        style = Typography.headlineSmall,
                    )
                }
            }
            Column(modifier = Modifier.fillMaxSize()) {
                AppDrawerActionItem(
                    icon = Icons.Filled.Search,
                    text = "Search",
                    onClick = onBreedClick,
                )
                AppDrawerActionItem(
                    icon = Icons.Default.PlayArrow,
                    text = "Play quiz",
                    onClick = onQuizClick,
                )
                AppDrawerActionItem(
                    icon = Icons.AutoMirrored.Filled.List,
                    text = "Leaderboard",
                    onClick = onLeaderboardClick,
                )

                AppDrawerActionItem(
                    icon = Icons.Default.Person,
                    text = "Profile",
                    onClick = onProfileClick,
                )

            }
        }
    }
}

@Composable
private fun AppDrawerActionItem(
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit)? = null,
) {
    ListItem(modifier = Modifier.clickable(enabled = onClick != null,
        onClick = { onClick?.invoke() }), leadingContent = {
        Icon(imageVector = icon, contentDescription = null)
    }, headlineContent = {
        Text(text = text)
    })
}