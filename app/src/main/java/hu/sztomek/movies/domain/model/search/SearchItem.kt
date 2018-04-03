package hu.sztomek.movies.domain.model.search

import hu.sztomek.movies.domain.model.DomainModel

data class SearchItem(
        val id: Int,
        val title: String?,
        val rating: Double?,
        val posterUrl: String?,
        val budget: Int?
) : DomainModel