package hu.sztomek.movies.domain.usecase

import hu.sztomek.movies.domain.DataSource
import hu.sztomek.movies.domain.model.search.SearchItem
import hu.sztomek.movies.domain.model.search.SearchResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class SearchUseCase @Inject constructor(private val dataSource: DataSource) {

    fun execute(query: String, page: Int, transformer: ObservableTransformer<SearchResult, SearchResult>): Observable<SearchResult> {
        val observable = Observable.create<SearchResult> { emitter ->
            try {
                val searchResult = dataSource.searchMovies(query, page)
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