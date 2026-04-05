package edu.nd.pmcburne.hello

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nd.pmcburne.hello.data.local.LocationEntity
import edu.nd.pmcburne.hello.data.repository.LocationRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repo: LocationRepository) : ViewModel() {

    var selectedTag by mutableStateOf("core")
        private set

    var tags by mutableStateOf(listOf<String>())
        private set

    var locations by mutableStateOf(listOf<LocationEntity>())
        private set

    init {
        viewModelScope.launch {
            repo.syncIfNeeded()
            tags = repo.getTags()
            loadLocations()
        }
    }

    fun onTagSelected(tag: String) {
        selectedTag = tag
        viewModelScope.launch {
            loadLocations()
        }
    }

    private suspend fun loadLocations() {
        locations = repo.getLocationsByTag(selectedTag)
    }
}