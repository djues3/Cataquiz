package rs.edu.raf.cataquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import rs.edu.raf.cataquiz.navigation.CataquizNavigation
import rs.edu.raf.cataquiz.profile.ProfileStore
import rs.edu.raf.cataquiz.ui.theme.CataquizTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    // Used to check if the user is logged in
    @Inject lateinit var profileStore: ProfileStore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CataquizTheme {
                CataquizNavigation(profileStore = profileStore)
            }

        }
    }
}