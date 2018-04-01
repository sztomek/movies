package hu.sztomek.movies.domain.model.search

data class SearchResult(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val items: List<SearchItem>
)