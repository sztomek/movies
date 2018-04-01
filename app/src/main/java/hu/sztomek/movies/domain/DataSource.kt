package hu.sztomek.movies.domain

import hu.sztomek.movies.domain.model.details.MovieDetails
import hu.sztomek.movies.domain.model.search.SearchResult
import io.reactivex.Observable

interface DataSource {

    fun searchMovies(query: String, page: Int): Observable<SearchResult>
    fun getMovieDetails(movieId: Int): Observable<MovieDetails>

}