package hu.sztomek.movies.domain.usecase

import hu.sztomek.movies.domain.DataSource
import hu.sztomek.movies.domain.model.details.MovieDetails
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class DetailsUseCase @Inject constructor(private val dataSource: DataSource) {

    fun execute(movieId: Int, transformer: ObservableTransformer<MovieDetails, MovieDetails>): Observable<MovieDetails> {
        return dataSource.getMovieDetails(movieId)
                .compose(transformer)
    }

}