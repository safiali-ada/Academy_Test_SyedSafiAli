package com.academytest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.academytest.ui.ContentScreen
import com.academytest.ui.theme.AcademyTestTheme
import com.academytest.viewmodels.ItemsListViewModel

/**
 * Single-Activity entry point — equivalent to @main AcademyTestApp.
 *
 * Creates the [ItemsListViewModel] at the Activity scope so it survives
 * configuration changes, then hands it to [ContentScreen].
 */
class MainActivity : ComponentActivity() {

    private val viewModel: ItemsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AcademyTestTheme {
                ContentScreen(viewModel = viewModel)
            }
        }
    }
}
