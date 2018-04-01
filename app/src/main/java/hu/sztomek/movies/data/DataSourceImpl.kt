package hu.sztomek.movies.data

import hu.sztomek.movies.data.converter.toDomainModel
import hu.sztomek.movies.domain.DataSource
import hu.sztomek.movies.domain.model.details.MovieDetails
import hu.sztomek.movies.domain.model.search.SearchResult
import io.reactivex.Observable

class DataSourceImpl(private val apiKey: String, private val restApi: RestApi) : DataSource {

    override fun searchMovies(query: String, page: Int): Observable<SearchResult> {
        return restApi.searchMovies(apiKey, query, page)
                .map {
                    it.toDomainModel()
                }
    }

    override fun getMovieDetails(movieId: Int): Observable<MovieDetails> {
        return restApi.getDetails(movieId, apiKey)
                .map {
                    it.toDomainModel()
                }
    }
}