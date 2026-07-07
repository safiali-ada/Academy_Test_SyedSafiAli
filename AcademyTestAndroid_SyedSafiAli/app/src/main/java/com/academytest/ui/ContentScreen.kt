package com.academytest.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.activity.compose.BackHandler
import com.academytest.ui.theme.AcademyTestTheme
import com.academytest.viewmodels.ItemState
import com.academytest.viewmodels.ItemsListViewModel

/**
 * Root content screen managing list ↔ detail navigation with
 * an iOS-like slide-in/out transition.
 *
 * Mirrors the SwiftUI ContentView which uses NavigationSplitView.
 * On Android, the detail screen slides in from the right as an overlay
 * (matching iOS push navigation) and slides back out on back press.
 */
@Composable
fun ContentScreen(
    viewModel: ItemsListViewModel,
    modifier: Modifier = Modifier
) {
    var showAddSheet by remember { mutableStateOf(false) }

    val selectedItem = viewModel.selectedItem
    val isDetailVisible = selectedItem != null

    // Remember the last selected item so the exit animation still
    // shows the correct content while the detail slides out.
    var lastSelectedItem by remember { mutableStateOf<ItemState?>(null) }
    if (selectedItem != null) {
        lastSelectedItem = selectedItem
    }

    Box(modifier = modifier.fillMaxSize()) {
        // ── List (always rendered behind the detail) ───────────
        ItemsListScreen(
            viewModel = viewModel,
            onItemClick = { item -> viewModel.selectItem(item.id) },
            onAddClick = { showAddSheet = true },
        )

        // ── Detail overlay with iOS push animation ─────────────
        AnimatedVisibility(
            visible = isDetailVisible,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it }),
        ) {
            val itemToShow = selectedItem ?: lastSelectedItem
            if (itemToShow != null) {
                BackHandler { viewModel.selectItem(null) }
                ItemDetailScreen(
                    item = itemToShow,
                    onDelete = { viewModel.deleteFromDetail(itemToShow) },
                    onBack = { viewModel.selectItem(null) },
                )
            }
        }
    }

    // ── Add-item bottom sheet ──────────────────────────────────
    if (showAddSheet) {
        AddItemSheet(
            onDismiss = { showAddSheet = false },
            onSave = { name ->
                viewModel.addItem(name)
                showAddSheet = false
            },
        )
    }
}

// ── Detail placeholder (matches iOS "Nessun oggetto" empty detail) ─

@Composable
private fun DetailPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.Inbox,
            contentDescription = null,
            modifier = Modifier.size(56.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Nessun oggetto",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Aggiungi un oggetto dalla lista.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 48.dp),
        )
    }
}

// ── Previews ───────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ContentScreenPreview() {
    AcademyTestTheme {
        ContentScreen(viewModel = ItemsListViewModel())
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ContentScreenDarkPreview() {
    AcademyTestTheme(darkTheme = true) {
        ContentScreen(viewModel = ItemsListViewModel())
    }
}
