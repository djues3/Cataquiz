package rs.edu.raf.cataquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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