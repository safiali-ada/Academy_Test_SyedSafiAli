package com.academytest.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.academytest.ui.theme.AcademyTestTheme
import com.academytest.viewmodels.ItemState

/**
 * A single list row displaying the item name, favorite subtitle,
 * and a [FavoriteButton]. Matches the SwiftUI ItemRowView layout.
 */
@Composable
fun ItemRowView(
    item: ItemState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = if (item.isFavorite) "Preferito" else "Non preferito",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        FavoriteButton(
            isFavorite = item.isFavorite,
            onToggle = { item.isFavorite = !item.isFavorite }
        )
    }
}

// ── Previews ───────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
private fun ItemRowViewPreview() {
    AcademyTestTheme {
        Column {
            ItemRowView(
                item = ItemState(
                    creationIndex = 0,
                    initialName = "Caffe",
                    initialIsFavorite = true
                )
            )
            ItemRowView(
                item = ItemState(
                    creationIndex = 1,
                    initialName = "Zaino",
                    initialIsFavorite = false
                )
            )
        }
    }
}
