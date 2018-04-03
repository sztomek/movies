package hu.sztomek.movies.domain.model.details

import hu.sztomek.movies.domain.model.DomainModel

data class MovieDetails(
        val id: Int,
        val title: String?,
        val posterUrl: String?,
        val budget: Int?,
        val releaseDate: String?,
        val totalRatings: Int?,
        val rating: Double?,
        val playTime: Int?,
        val company: String?
) : DomainModel