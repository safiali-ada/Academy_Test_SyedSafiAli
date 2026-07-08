package com.academytest.viewmodels

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for [ItemState].
 *
 * Verifies identity semantics (UUID-based equality)
 * and mutable Compose state behaviour.
 */
class ItemStateTest {

    @Test
    fun `items with different UUIDs are not equal`() {
        val a = ItemState(creationIndex = 0, initialName = "A", initialIsFavorite = false)
        val b = ItemState(creationIndex = 0, initialName = "A", initialIsFavorite = false)
        assertNotEquals(a, b) // different auto-generated UUIDs
    }

    @Test
    fun `item equals itself`() {
        val item = ItemState(creationIndex = 0, initialName = "Test", initialIsFavorite = false)
        assertEquals(item, item)
    }

    @Test
    fun `name can be mutated`() {
        val item = ItemState(creationIndex = 0, initialName = "Old", initialIsFavorite = false)
        item.name = "New"
        assertEquals("New", item.name)
    }

    @Test
    fun `isFavorite can be toggled`() {
        val item = ItemState(creationIndex = 0, initialName = "Test", initialIsFavorite = false)
        assertFalse(item.isFavorite)
        item.isFavorite = true
        assertTrue(item.isFavorite)
    }

    @Test
    fun `hashCode is consistent with equals`() {
        val item = ItemState(creationIndex = 0, initialName = "Test", initialIsFavorite = false)
        val sameRef = item
        assertEquals(item.hashCode(), sameRef.hashCode())
    }
}
