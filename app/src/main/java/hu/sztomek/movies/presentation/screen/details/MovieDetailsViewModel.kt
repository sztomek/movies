package hu.sztomek.movies.presentation.screen.details

import hu.sztomek.movies.domain.action.Action
import hu.sztomek.movies.domain.action.Operation
import hu.sztomek.movies.domain.action.SearchAction
import hu.sztomek.movies.presentation.common.BaseViewModel
import hu.sztomek.movies.presentation.common.UiState
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor() : BaseViewModel() {

    override fun invokeActions(): ObservableTransformer<Action, Operation> {
        return ObservableTransformer { upstream ->
            upstream.flatMap {
                Observable.just(Operation.InProgress(SearchAction("alma", 0))) // TODO dummy
            }
        }
    }

    override fun getReducerFunction(): BiFunction<UiState, in Operation, UiState> {
        return BiFunction { oldState, operation ->
            oldState
        }
    }
}