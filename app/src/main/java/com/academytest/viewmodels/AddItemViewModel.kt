package com.academytest.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**ViewModel== @Observable in SwiftUI in iOS**/

class AddItemViewModel : ViewModel() {
    var name by mutableStateOf("")

    val trimmedName: String
        get() = name.trim()

    val canSave: Boolean
        get() = trimmedName.isNotEmpty()
}
