package hu.sztomek.movies.domain.usecase

import hu.sztomek.movies.domain.DataSource
import hu.sztomek.movies.domain.action.GetDetailsAction
import hu.sztomek.movies.domain.model.details.MovieDetails
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class DetailsUseCase @Inject constructor(private val dataSource: DataSource): UseCase<GetDetailsAction, MovieDetails> {

    override fun execute(action: GetDetailsAction, transformer: ObservableTransformer<MovieDetails, MovieDetails>): Observable<MovieDetails> {
        return dataSource.getMovieDetails(action.movieId)
                .compose(transformer)
    }

}