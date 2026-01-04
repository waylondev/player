package dev.waylon.player.ui.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.waylon.player.model.VideoInfo
import dev.waylon.player.service.ServiceProvider
import dev.waylon.player.ui.components.VideoCard

/**
 * SearchScreen state data class for unified state management
 */
typealias SearchScreenState = BaseSearchScreenState<VideoInfo>

/**
 * Search videos screen
 */
@Composable
fun SearchScreen(
    isRefreshing: Boolean,
    onRefreshComplete: () -> Unit,
    onVideoClick: (String) -> Unit
) {
    // Search state
    var screenState by remember { mutableStateOf(SearchScreenState()) }

    // Execute search (initial search and refresh)
    LaunchedEffect(screenState.searchKeyword, isRefreshing) {
        // If no search keyword, only handle refresh
        if (screenState.searchKeyword.isBlank() && !isRefreshing) {
            screenState = screenState.clearResults()
            onRefreshComplete()
            return@LaunchedEffect
        }

        // If refresh state but no search keyword, complete refresh directly
        if (isRefreshing && screenState.searchKeyword.isBlank()) {
            onRefreshComplete()
            return@LaunchedEffect
        }

        // If search keyword exists, execute search
        if (screenState.searchKeyword.isNotBlank()) {
            try {
                screenState = screenState.updateSearching(true)
                // Call unified API to search videos
                val result = ServiceProvider.videoService.searchVideos(
                    keyword = screenState.searchKeyword,
                    pageSize = 20,
                    page = 1 // Always search first page when keyword changes
                )
                screenState = screenState.updateSearchResults(result)
            } catch (e: Exception) {
                screenState = screenState.updateSearchResults(emptyList(), "Search failed: ${e.message}")
            } finally {
                screenState = screenState.updateSearching(false)
                onRefreshComplete()
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar
        OutlinedTextField(
            value = screenState.searchKeyword,
            onValueChange = { screenState = screenState.updateSearchKeyword(it) },
            label = { Text(text = "Search videos") },
            placeholder = { Text(text = "Enter video title, UP name, etc.") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (screenState.searchKeyword.isNotBlank()) {
                    IconButton(onClick = { screenState = screenState.updateSearchKeyword("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear"
                        )
                    }
                }
            },
            singleLine = true,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Search results or status display
        if (screenState.isSearching) {
            // Searching state
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Searching...",
                    modifier = Modifier.padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } else if (screenState.searchKeyword.isBlank()) {
            // Search prompt
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Please enter search keyword",
                    modifier = Modifier.padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else if (screenState.errorMessage != null) {
            // Error message
            Text(
                text = screenState.errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        } else if (screenState.searchResults.isEmpty()) {
            // No results prompt
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    text = "No related videos found",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // Search results list - grid layout
            val gridState = rememberLazyGridState()
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(minSize = 200.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(screenState.searchResults) {
                    VideoCard(
                        video = it,
                        modifier = Modifier.padding(8.dp).clickable {
                            onVideoClick(it.id)
                        }
                    )
                }
            }
        }
    }
}
