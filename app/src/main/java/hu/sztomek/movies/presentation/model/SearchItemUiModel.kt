package hu.sztomek.movies.presentation.model

data class SearchItemUiModel(
        val movieId: Int,
        val title: String,
        val posterUrl: String?,
        val rating: String,
        val budget: String
)