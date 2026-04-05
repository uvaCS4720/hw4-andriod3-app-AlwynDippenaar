package edu.nd.pmcburne.hello

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {

        TagDropdown(
            tags = viewModel.tags,
            selected = viewModel.selectedTag,
            onSelect = { viewModel.onTagSelected(it) }
        )

        MapView(
            locations = viewModel.locations
        )
    }
}