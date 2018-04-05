package hu.sztomek.movies.data.converter

import hu.sztomek.movies.data.model.details.MovieDetailsResponse
import hu.sztomek.movies.data.model.details.ProductionCompany
import hu.sztomek.movies.data.model.search.SearchMoviesResponse
import hu.sztomek.movies.data.model.search.SearchMoviesResult
import hu.sztomek.movies.domain.model.details.MovieDetails
import hu.sztomek.movies.domain.model.search.SearchItem
import hu.sztomek.movies.domain.model.search.SearchResult

private fun defaultPosterPathConverter(prefixUrl: String, posterPath: String?): String? {
    return if (posterPath != null) prefixUrl + posterPath else null
}

private fun defaultCompaniesConverter(companies: List<ProductionCompany>?): String? {
    return if (companies?.isEmpty() == false) companies[0].name else null
}

fun MovieDetailsResponse.toDomainModel(
        posterPrefixUrl: String
) = MovieDetails(
        this.id!!,
        this.title,
        defaultPosterPathConverter(posterPrefixUrl, posterPath),
        this.budget,
        this.releaseDate,
        this.voteCount,
        this.voteAverage,
        this.runtime,
        defaultCompaniesConverter(productionCompanies)
)

fun SearchMoviesResult.toDomainModel(
        budget: Int? = null,
        posterPrefixUrl: String
) = SearchItem(
        this.id!!,
        this.title,
        this.voteAverage,
        defaultPosterPathConverter(posterPrefixUrl, posterPath),
        budget
)

fun SearchMoviesResponse.toDomainModel(
        posterPrefixUrl: String
): SearchResult {
    val items: MutableList<SearchItem> = mutableListOf()
    this.results?.forEach {
        items.add(
                it.toDomainModel(posterPrefixUrl = posterPrefixUrl)
        )
    }

    return SearchResult(
            this.page ?: 1,
            this.totalPages ?: 1,
            this.totalResults ?: 0,
            items.toList()
    )
}