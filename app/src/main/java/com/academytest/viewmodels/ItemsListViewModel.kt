package com.academytest.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.UUID

/**
 * Uses [mutableStateListOf] so Compose observes list mutations,
 * and [mutableStateOf] for the selected-item ID.
 */
class ItemsListViewModel : ViewModel() {

    private val _items = mutableStateListOf<ItemState>()
    val items: List<ItemState> get() = _items

    var selectedItemId: UUID? by mutableStateOf(null)
        private set

    private var nextCreationIndex: Int

    init {
        _items.addAll(defaultItems)
        nextCreationIndex = (_items.maxOfOrNull { it.creationIndex } ?: 0) + 1
    }

    /** Items sorted alphabetically and then by order of creation */
    val sortedItems: List<ItemState>
        get() = _items.sortedWith(
            compareBy(String.CASE_INSENSITIVE_ORDER) { item: ItemState -> item.name }
                .thenBy { it.creationIndex }
        )

    /** The currently selected item, if any. */
    val selectedItem: ItemState?
        get() = selectedItemId?.let { id -> _items.find { it.id == id } }

    fun selectItem(id: UUID?) {
        selectedItemId = id
    }

    fun addItem(name: String): ItemState {
        val trimmedName = name.trim()
        val item = ItemState(
            creationIndex = nextCreationIndex,
            initialName = trimmedName,
            initialIsFavorite = false
        )
        nextCreationIndex++
        _items.add(item)
        return item
    }

    fun deleteItems(indices: Set<Int>) {
        val sorted = sortedItems
        val idsToDelete = indices.map { sorted[it].id }.toSet()
        deleteItemsByIds(idsToDelete)
    }

    fun delete(item: ItemState) {
        deleteItemsByIds(setOf(item.id))
    }

    /** Delete from the detail view — clears selection and navigates back. */
    fun deleteFromDetail(item: ItemState) {
        _items.removeAll { it.id == item.id }
        selectedItemId = null
    }

    private fun deleteItemsByIds(idsToDelete: Set<UUID>) {
        _items.removeAll { idsToDelete.contains(it.id) }

        if (selectedItemId != null && idsToDelete.contains(selectedItemId)) {
            selectedItemId = sortedItems.firstOrNull()?.id
        }
    }

    companion object {
        val defaultItems = listOf(
            ItemState(creationIndex = 0, initialName = "Lupo 🐺", initialIsFavorite = true),
            ItemState(creationIndex = 1, initialName = "Giraffa 🦒", initialIsFavorite = false),
            ItemState(creationIndex = 2, initialName = "Leone 🦁", initialIsFavorite = false)
        )
    }
}
