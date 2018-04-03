package hu.sztomek.movies.domain.model.search

import hu.sztomek.movies.domain.model.DomainModel

data class SearchResult(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val items: List<SearchItem>
) : DomainModel