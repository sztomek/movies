package hu.sztomek.movies.domain.usecase

import hu.sztomek.movies.domain.data.DataSource
import hu.sztomek.movies.domain.action.SearchAction
import hu.sztomek.movies.domain.model.search.SearchItem
import hu.sztomek.movies.domain.model.search.SearchResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val dataSource: DataSource) : UseCase<SearchAction, SearchResult> {

    override fun execute(action: SearchAction, transformer: ObservableTransformer<SearchResult, SearchResult>): Observable<SearchResult> {
        val observable = Observable.create<SearchResult> { emitter ->
            try {
                val searchResult = dataSource.searchMovies(action.query, action.page)
                        .blockingFirst()
                val items = mutableListOf<SearchItem>()
                searchResult.items.forEach {
                    items.add(
                            it.copy(
                                    budget = dataSource.getMovieDetails(it.id)
                                            .blockingFirst()
                                            .budget
                            )
                    )
                }
                emitter.onNext(
                        searchResult.copy(items = items.toList())
                )
            } catch (e: Throwable) {
                emitter.onError(e)
            }
        }
        return observable
                .compose(transformer)
    }

}