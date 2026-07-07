package com.academytest.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.academytest.ui.theme.AcademyTestTheme
import com.academytest.ui.theme.IOSGray
import com.academytest.ui.theme.IOSYellow

/**
 * Star toggle button matching the iOS FavoriteButton.
 *
 * Filled yellow star when [isFavorite] is true,
 * outlined grey star when false.
 */
@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val label = if (isFavorite) "Rimuovi dai preferiti" else "Aggiungi ai preferiti"

    IconButton(
        onClick = onToggle,
        modifier = modifier.semantics { contentDescription = label }
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
            contentDescription = null, // handled by the button semantics
            tint = if (isFavorite) IOSYellow else IOSGray,
            modifier = Modifier.size(24.dp)
        )
    }
}

// ── Previews ───────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
private fun FavoriteButtonPreview() {
    AcademyTestTheme {
        var isFavorite by remember { mutableStateOf(true) }
        FavoriteButton(isFavorite = isFavorite, onToggle = { isFavorite = !isFavorite })
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteButtonNotFavoritePreview() {
    AcademyTestTheme {
        var isFavorite by remember { mutableStateOf(false) }
        FavoriteButton(isFavorite = isFavorite, onToggle = { isFavorite = !isFavorite })
    }
}
