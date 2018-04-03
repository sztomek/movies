package hu.sztomek.movies.presentation.converter

import hu.sztomek.movies.domain.model.search.SearchItem
import hu.sztomek.movies.presentation.model.SearchItemUiModel

fun SearchItem.toUiModel() = SearchItemUiModel(
        id,
        title,
        posterUrl,
        rating,
        budget
)