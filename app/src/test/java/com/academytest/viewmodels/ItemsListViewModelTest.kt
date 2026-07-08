package com.academytest.viewmodels

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [ItemsListViewModel].
 *
 * Covers the core list operations: initial state, adding, deleting,
 * sorting, and selection — mirroring the behaviours that map from
 * the iOS @Observable ItemsListViewModel.
 */
class ItemsListViewModelTest {

    private lateinit var viewModel: ItemsListViewModel

    @Before
    fun setUp() {
        viewModel = ItemsListViewModel()
    }

    // ── Initial state ──────────────────────────────────────────────

    @Test
    fun `initial items contain three default entries`() {
        assertEquals(3, viewModel.items.size)
    }

    @Test
    fun `default items have expected names`() {
        val names = viewModel.items.map { it.name }.toSet()
        assertTrue("Lupo 🐺" in names)
        assertTrue("Giraffa 🦒" in names)
        assertTrue("Leone 🦁" in names)
    }

    // ── Sorting ────────────────────────────────────────────────────

    @Test
    fun `sortedItems returns items in alphabetical order`() {
        val sorted = viewModel.sortedItems
        assertEquals("Giraffa 🦒", sorted[0].name)
        assertEquals("Leone 🦁", sorted[1].name)
        assertEquals("Lupo 🐺", sorted[2].name)
    }

    // ── Adding items ───────────────────────────────────────────────

    @Test
    fun `addItem increases list size by one`() {
        val before = viewModel.items.size
        viewModel.addItem("Panda 🐼")
        assertEquals(before + 1, viewModel.items.size)
    }

    @Test
    fun `addItem trims whitespace from name`() {
        val item = viewModel.addItem("  Gatto 🐱  ")
        assertEquals("Gatto 🐱", item.name)
    }

    @Test
    fun `addItem returns new item with isFavorite false`() {
        val item = viewModel.addItem("Delfino 🐬")
        assertFalse(item.isFavorite)
    }

    @Test
    fun `added item appears in sortedItems`() {
        viewModel.addItem("Aquila 🦅")
        val names = viewModel.sortedItems.map { it.name }
        assertTrue("Aquila 🦅" in names)
        // Aquila sorts first alphabetically
        assertEquals("Aquila 🦅", viewModel.sortedItems.first().name)
    }

    // ── Deleting items ─────────────────────────────────────────────

    @Test
    fun `deleteItems removes item at sorted index`() {
        val before = viewModel.items.size
        val firstSortedName = viewModel.sortedItems[0].name
        viewModel.deleteItems(setOf(0))
        assertEquals(before - 1, viewModel.items.size)
        // The deleted item should be gone
        assertFalse(viewModel.items.any { it.name == firstSortedName })
    }

    @Test
    fun `deleteFromDetail removes item and clears selection`() {
        val item = viewModel.items[0]
        viewModel.selectItem(item.id)
        assertNotNull(viewModel.selectedItem)

        viewModel.deleteFromDetail(item)
        assertNull(viewModel.selectedItem)
        assertFalse(viewModel.items.any { it.id == item.id })
    }

    // ── Selection ──────────────────────────────────────────────────

    @Test
    fun `selectItem sets selectedItem`() {
        val item = viewModel.items[1]
        viewModel.selectItem(item.id)
        assertEquals(item, viewModel.selectedItem)
    }

    @Test
    fun `selectItem with null clears selection`() {
        viewModel.selectItem(viewModel.items[0].id)
        assertNotNull(viewModel.selectedItem)

        viewModel.selectItem(null)
        assertNull(viewModel.selectedItem)
    }

    @Test
    fun `selectedItem is null initially`() {
        assertNull(viewModel.selectedItem)
    }
}
