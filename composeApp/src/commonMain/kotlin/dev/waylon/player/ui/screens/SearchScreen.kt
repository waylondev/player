package dev.waylon.player.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.waylon.player.model.VideoInfo
import dev.waylon.player.service.ServiceProvider
import dev.waylon.player.ui.components.VideoCard
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

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
    var searchKeyword by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<VideoInfo>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }
    var isLoadingMore by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var page by remember { mutableStateOf(1) }
    var hasMore by remember { mutableStateOf(true) }

    // Grid state for loading more
    val gridState = rememberLazyGridState()

    // Execute search (initial search and refresh)
    LaunchedEffect(searchKeyword, isRefreshing) {
        // If no search keyword, only handle refresh
        if (searchKeyword.isBlank() && !isRefreshing) {
            searchResults = emptyList()
            errorMessage = null
            onRefreshComplete()
            return@LaunchedEffect
        }

        // If refresh state but no search keyword, complete refresh directly
        if (isRefreshing && searchKeyword.isBlank()) {
            onRefreshComplete()
            return@LaunchedEffect
        }

        // If search keyword exists, execute search
        if (searchKeyword.isNotBlank()) {
            try {
                isSearching = true
                errorMessage = null
                page = 1 // Reset page number
                hasMore = true // Reset whether there is more data
                // Call unified API to search videos
                val result = ServiceProvider.videoService.searchVideos(
                    keyword = searchKeyword,
                    pageSize = 20,
                    page = page
                )
                searchResults = result
                hasMore = result.size >= 20 // If returned data is less than pageSize, no more data
            } catch (e: Exception) {
                errorMessage = "Search failed: ${e.message}"
                searchResults = emptyList()
            } finally {
                isSearching = false
                onRefreshComplete()
            }
        }
    }

    // Load more videos
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(gridState) {
        snapshotFlow {
            val visibleItems = gridState.layoutInfo.visibleItemsInfo
            if (visibleItems.isNotEmpty()) visibleItems.last().index else -1
        }
            .distinctUntilChanged()
            .collect { lastVisibleIndex: Int ->
                if (hasMore && !isLoadingMore && !isSearching && lastVisibleIndex >= gridState.layoutInfo.totalItemsCount - 5) {
                    // Load more
                    coroutineScope.launch {
                        try {
                            isLoadingMore = true
                            page += 1
                            val result = ServiceProvider.videoService.searchVideos(
                                keyword = searchKeyword,
                                pageSize = 20,
                                page = page
                            )
                            searchResults = searchResults + result
                            hasMore = result.size >= 20
                        } catch (e: Exception) {
                            errorMessage = "Load more failed: ${e.message}"
                        } finally {
                            isLoadingMore = false
                        }
                    }
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search bar
        OutlinedTextField(
            value = searchKeyword,
            onValueChange = { searchKeyword = it },
            label = { Text(text = "Search videos") },
            placeholder = { Text(text = "Enter video title, UP name, etc.") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (searchKeyword.isNotBlank()) {
                    IconButton(onClick = { searchKeyword = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear"
                        )
                    }
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Search results or status display
        if (isSearching) {
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
        } else if (searchKeyword.isBlank()) {
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
        } else if (errorMessage != null) {
            // Error message
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        } else if (searchResults.isEmpty()) {
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
            // Search results list - adaptive layout, minimum card width 200dp
            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(minSize = 200.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(searchResults) {
                    VideoCard(
                        video = it,
                        modifier = Modifier.padding(8.dp).clickable {
                            onVideoClick(it.id)
                        }
                    )
                }

                // Load more indicator
                if (isLoadingMore) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Loading more...",
                                modifier = Modifier.padding(top = 8.dp),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
