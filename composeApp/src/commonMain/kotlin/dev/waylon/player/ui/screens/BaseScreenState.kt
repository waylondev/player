package dev.waylon.player.ui.screens

/**
 * Base screen state class for unified state management across all screens
 */
open class BaseScreenState {
    /**
     * Resets the state to its initial values
     */
    open fun reset(): BaseScreenState {
        return this
    }
}

/**
 * Base state class for screens that display a list of items
 */
data class BaseListScreenState<T>(
    val items: List<T> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val errorMessage: String? = null
) : BaseScreenState() {
    /**
     * Updates the loading state
     */
    fun updateLoading(loading: Boolean): BaseListScreenState<T> {
        return copy(isLoading = loading)
    }

    /**
     * Updates the items list and error message
     */
    fun updateItems(items: List<T>, errorMessage: String? = null): BaseListScreenState<T> {
        return copy(
            items = items,
            errorMessage = errorMessage
        )
    }

    /**
     * Updates the loading more state
     */
    fun updateLoadingMore(loadingMore: Boolean): BaseListScreenState<T> {
        return copy(isLoadingMore = loadingMore)
    }

    /**
     * Appends new items to the list
     */
    fun appendItems(newItems: List<T>): BaseListScreenState<T> {
        return copy(items = items + newItems)
    }

    /**
     * Clears the items list and error message
     */
    fun clearItems(): BaseListScreenState<T> {
        return copy(
            items = emptyList(),
            errorMessage = null
        )
    }

    override fun reset(): BaseListScreenState<T> {
        return BaseListScreenState()
    }
}

/**
 * Base state class for screens that perform search operations
 */
data class BaseSearchScreenState<T>(
    val searchKeyword: String = "",
    val searchResults: List<T> = emptyList(),
    val isSearching: Boolean = false,
    val errorMessage: String? = null
) : BaseScreenState() {
    /**
     * Updates the search keyword
     */
    fun updateSearchKeyword(keyword: String): BaseSearchScreenState<T> {
        return copy(searchKeyword = keyword)
    }

    /**
     * Updates the searching state
     */
    fun updateSearching(searching: Boolean): BaseSearchScreenState<T> {
        return copy(isSearching = searching)
    }

    /**
     * Updates the search results and error message
     */
    fun updateSearchResults(results: List<T>, errorMessage: String? = null): BaseSearchScreenState<T> {
        return copy(
            searchResults = results,
            errorMessage = errorMessage
        )
    }

    /**
     * Clears the search results
     */
    fun clearResults(): BaseSearchScreenState<T> {
        return copy(
            searchResults = emptyList(),
            errorMessage = null
        )
    }

    override fun reset(): BaseSearchScreenState<T> {
        return BaseSearchScreenState()
    }
}
