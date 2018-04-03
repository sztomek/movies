package hu.sztomek.movies.data.converter

import hu.sztomek.movies.data.model.details.MovieDetailsResponse
import hu.sztomek.movies.data.model.search.SearchMoviesResponse
import hu.sztomek.movies.data.model.search.SearchMoviesResult
import hu.sztomek.movies.domain.model.details.MovieDetails
import hu.sztomek.movies.domain.model.search.SearchItem
import hu.sztomek.movies.domain.model.search.SearchResult

fun MovieDetailsResponse.toDomainModel(posterUrl: String) = MovieDetails(
        this.id!!,
        this.title,
        posterUrl + this.posterPath,
        this.budget,
        this.releaseDate,
        this.voteCount,
        this.voteAverage,
        this.runtime,
        this.productionCompanies?.get(0)?.name
)

fun SearchMoviesResult.toDomainModel(budget: Int? = null, posterUrl: String) = SearchItem(
        this.id!!,
        this.title,
        this.voteAverage,
        posterUrl + this.posterPath,
        budget
)

fun SearchMoviesResponse.toDomainModel(posterUrl: String): SearchResult {
    val items: MutableList<SearchItem> = mutableListOf()
    this.results?.forEach { items.add(it.toDomainModel(posterUrl = posterUrl)) }

    return SearchResult(
            this.page ?: 0,
            this.totalPages ?: 0,
            this.totalResults ?: 0,
            items.toList()
    )
}