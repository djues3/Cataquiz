package rs.edu.raf.cataquiz.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    contentDescription: String? = null,
    tint: Color = LocalContentColor.current,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = tint,
        )
    }
}

@Composable
fun AppDropdownMenuItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        leadingIcon = { Icon(imageVector = icon, contentDescription = text) },
        text = { Text(text = text) },
        onClick = onClick,
    )
}

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    text: String
) {
    ElevatedCard(modifier = Modifier.padding(8.dp)) {
        Text(
            text = text,
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Justify,
            fontSize = 14.sp
        )
    }
}
@Composable
fun RatingBar(
    rating: Float,
    maxRating: Int = 5,
) {
    Row {
        for (i in 1..maxRating) {
            if (i <= rating) {
                Icon(Icons.Filled.StarRate, "Filled star")
            } else {
                Icon(Icons.Outlined.StarRate, "Non-filled star")
            }
        }
    }
}