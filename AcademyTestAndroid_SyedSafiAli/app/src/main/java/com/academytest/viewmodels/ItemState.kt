package com.academytest.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.UUID

/**
 * Represents a single item with observable mutable state.
 * Equivalent to the SwiftUI @Observable ItemViewModel class.
 *
 * Uses Compose [mutableStateOf] so that any composable reading
 * [name] or [isFavorite] automatically recomposes on changes —
 * mirroring Swift's @Observable reactivity.
 */
class ItemState(
    val id: UUID = UUID.randomUUID(),
    val creationIndex: Int,
    initialName: String,
    initialIsFavorite: Boolean
) {
    var name by mutableStateOf(initialName)
    var isFavorite by mutableStateOf(initialIsFavorite)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ItemState) return false
        return id == other.id
    }

    override fun hashCode(): Int = id.hashCode()
}
