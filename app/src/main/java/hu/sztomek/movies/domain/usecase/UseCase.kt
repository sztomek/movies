package hu.sztomek.movies.domain.usecase

import hu.sztomek.movies.domain.action.Action
import hu.sztomek.movies.domain.model.DomainModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

interface UseCase<A: Action, M: DomainModel> {

    fun execute(action: A, transformer: ObservableTransformer<M, M>): Observable<M>

}