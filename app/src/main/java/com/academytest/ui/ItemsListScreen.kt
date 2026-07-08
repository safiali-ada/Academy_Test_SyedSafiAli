package com.academytest.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.academytest.ui.theme.AcademyTestTheme
import com.academytest.ui.theme.IOSRed
import com.academytest.viewmodels.ItemState
import com.academytest.viewmodels.ItemsListViewModel
import androidx.compose.ui.res.stringResource
import com.academytest.R

/**
 * List screen displaying sorted items with swipe-to-delete,
 * an add button in the toolbar, and an iOS-style empty state.
 *
 * Mirrors the SwiftUI ItemsListView.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsListScreen(
    viewModel: ItemsListViewModel,
    onItemClick: (ItemState) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            ItemsListTopBar(
                scrollBehavior = scrollBehavior,
                onAddClick = onAddClick,
            )
        }
    ) { innerPadding ->
        if (viewModel.items.isEmpty()) {
            EmptyStateView(
                onAddClick = onAddClick,
                modifier = Modifier.padding(innerPadding),
            )
        } else {
            ItemsList(
                sortedItems = viewModel.sortedItems,
                onItemClick = onItemClick,
                onDeleteItem = { index -> viewModel.deleteItems(setOf(index)) },
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

// ── Top App Bar ────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemsListTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onAddClick: () -> Unit,
) {
    LargeTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.title_items),
                style = MaterialTheme.typography.headlineLarge,
            )
        },
        actions = {
            IconButton(onClick = onAddClick) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.cd_add_item),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
    )
}

// ── Items List ─────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ItemsList(
    sortedItems: List<ItemState>,
    onItemClick: (ItemState) -> Unit,
    onDeleteItem: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }

        itemsIndexed(
            items = sortedItems,
            key = { _, item -> item.id }
        ) { index, item ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { value ->
                    if (value == SwipeToDismissBoxValue.EndToStart) {
                        onDeleteItem(index)
                        true
                    } else {
                        false
                    }
                }
            )

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    val color by animateColorAsState(
                        targetValue = when (dismissState.targetValue) {
                            SwipeToDismissBoxValue.EndToStart -> IOSRed
                            else -> Color.Transparent
                        },
                        label = "swipeBg",
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color,
                                shape = when {
                                    sortedItems.size == 1 -> RoundedCornerShape(10.dp)
                                    index == 0 -> RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                                    index == sortedItems.lastIndex -> RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                                    else -> RoundedCornerShape(0.dp)
                                }
                            ),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(R.string.cd_delete),
                            tint = Color.White,
                            modifier = Modifier.padding(end = 20.dp),
                        )
                    }
                },
                enableDismissFromStartToEnd = false,
            ) {
                val shape = when {
                    sortedItems.size == 1 -> RoundedCornerShape(10.dp)
                    index == 0 -> RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    index == sortedItems.lastIndex -> RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    else -> RoundedCornerShape(0.dp)
                }

                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = shape,
                ) {
                    Column {
                        ItemRowView(
                            item = item,
                            modifier = Modifier.clickable { onItemClick(item) },
                        )
                        if (index < sortedItems.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(start = 16.dp),
                                thickness = 0.5.dp,
                                color = MaterialTheme.colorScheme.outlineVariant,
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

// ── Empty State ────────────────────────────────────────────────────

/**
 * Centered empty state matching iOS ContentUnavailableView.
 */
@Composable
private fun EmptyStateView(
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            text = stringResource(R.string.empty_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.empty_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 48.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = onAddClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
            ),
        ) {
            Text(
                stringResource(R.string.action_add_item),
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

// ── Previews ───────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ItemsListScreenPreview() {
    AcademyTestTheme {
        ItemsListScreen(
            viewModel = ItemsListViewModel(),
            onItemClick = {},
            onAddClick = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ItemsListScreenDarkPreview() {
    AcademyTestTheme(darkTheme = true) {
        ItemsListScreen(
            viewModel = ItemsListViewModel(),
            onItemClick = {},
            onAddClick = {},
        )
    }
}
