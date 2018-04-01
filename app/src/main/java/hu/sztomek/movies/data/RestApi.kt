package hu.sztomek.movies.data

import hu.sztomek.movies.data.model.details.MovieDetailsResponse
import hu.sztomek.movies.data.model.search.SearchMoviesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestApi {

    @GET("search/movie")
    fun searchMovies(
            @Query("api_key") apiKey: String,
            @Query("query") query: String,
            @Query("page") page: Int
    ) : Observable<SearchMoviesResponse>

    @GET("movie/{movie_id}")
    fun getDetails(
            @Path("movie_id") movieId: Int,
            @Query("api_key") apiKey: String
    ) : Observable<MovieDetailsResponse>

}