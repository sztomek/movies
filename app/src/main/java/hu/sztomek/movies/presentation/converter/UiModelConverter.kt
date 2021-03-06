package hu.sztomek.movies.presentation.converter

import hu.sztomek.movies.domain.model.details.MovieDetails
import hu.sztomek.movies.domain.model.search.SearchItem
import hu.sztomek.movies.presentation.model.MovieDetailsUiModel
import hu.sztomek.movies.presentation.model.SearchItemUiModel

private fun defaultTitleConverter(title: String?): String {
    return title ?: "N/A"
}

private fun defaultRatingConverter(rating: Double?): String {
    return if (rating == null) "N/A" else "$rating / 10"
}

private fun defaultBudgetConverter(budget: Int?): String {
    return when (budget) {
        null -> "N/A"
        in 0..999 -> budget.toString()
        in 1_000..1_000_000 -> "${"%.2f".format(budget / 1_000.0)}k"
        else -> "${"%.2f".format(budget / 1_000_000.0)}M"
    }
}

private fun defaultPlayTimeConverter(playTime: Int?): String {
    return if (playTime == null) "N/A" else "${playTime/60}:${playTime%60}"
}

fun SearchItem.toUiModel(
        titleConverter: ((title: String?) -> String) = ::defaultTitleConverter,
        ratingConverter: ((rating: Double?) -> String) = ::defaultRatingConverter,
        budgetConverter: ((budget: Int?) -> String) = ::defaultBudgetConverter
) = SearchItemUiModel(
        id,
        titleConverter(title),
        posterUrl,
        ratingConverter(rating),
        budgetConverter(budget)
)

fun MovieDetails.toUiModel(
        titleConverter: ((title: String?) -> String) = ::defaultTitleConverter,
        playTimeConverter: ((playTime: Int?) -> String) = ::defaultPlayTimeConverter,
        ratingConverter: ((rating: Double?) -> String) = ::defaultRatingConverter,
        budgetConverter: ((budget: Int?) -> String) = ::defaultBudgetConverter
) = MovieDetailsUiModel(
        id,
        titleConverter(title),
        playTimeConverter(playTime),
        releaseDate,
        company,
        ratingConverter(rating),
        budgetConverter(budget),
        homePage,
        posterUrl
)