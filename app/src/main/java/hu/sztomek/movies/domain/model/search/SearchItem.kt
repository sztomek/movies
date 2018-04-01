package hu.sztomek.movies.domain.model.search

data class SearchItem(
        val id: Int,
        val title: String?,
        val rating: Double?,
        val posterUrl: String?,
        val budget: Int?
)