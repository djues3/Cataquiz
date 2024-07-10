package rs.edu.raf.cataquiz.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    background = LightBackground,
    onBackground = LightForeground,
    surface = LightCard,
    onSurface = LightCardForeground,
    primary = LightPrimary,
    onPrimary = LightPrimaryForeground,
    secondary = LightSecondary,
    onSecondary = LightSecondaryForeground,
    error = LightDestructive,
    onError = LightDestructiveForeground,
    outline = LightBorder,
    tertiary = LightInput
)

private val LightColorScheme = lightColorScheme(
    background = DarkBackground,
    onBackground = DarkForeground,
    surface = DarkCard,
    onSurface = DarkCardForeground,
    primary = DarkPrimary,
    onPrimary = DarkPrimaryForeground,
    secondary = DarkSecondary,
    onSecondary = DarkSecondaryForeground,
    error = DarkDestructive,
    onError = DarkDestructiveForeground,
    outline = DarkBorder,
    tertiary = DarkInput
)

@Composable
fun CataquizTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography, content = content
    )
}