package hu.sztomek.movies.presentation.screen.search

import hu.sztomek.movies.domain.Operation
import hu.sztomek.movies.domain.action.Action
import hu.sztomek.movies.domain.action.SearchAction
import hu.sztomek.movies.domain.model.search.SearchResult
import hu.sztomek.movies.domain.usecase.SearchUseCase
import hu.sztomek.movies.presentation.common.BaseViewModel
import hu.sztomek.movies.presentation.common.UiState
import hu.sztomek.movies.presentation.converter.toUiModel
import hu.sztomek.movies.presentation.model.SearchItemUiModel
import hu.sztomek.movies.presentation.model.SearchUiModel
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : BaseViewModel() {

    override fun invokeActions(): ObservableTransformer<Action, Operation> {
        return ObservableTransformer { upstream ->
            upstream.ofType(SearchAction::class.java)
                    .flatMap {
                        searchUseCase.execute(it, ObservableTransformer { it.observeOn(Schedulers.computation()) })
                                .map { res -> Operation.CompletedSuccessfully(it, res) as Operation }
                                .onErrorReturn { t -> Operation.Failed(it, t) }
                                .startWith(Operation.InProgress(it))
                    }
        }
    }

    override fun getReducerFunction(): BiFunction<UiState, in Operation, UiState> {
        return BiFunction { oldState: UiState, operation: Operation ->
            when (operation) {
                is Operation.InProgress -> UiState.LoadingState("Please wait", oldState.data) // TODO resource use case!
                is Operation.Failed -> {
                    when (operation.action) {
                        is SearchAction -> UiState.ErrorState(operation.throwable.message
                                ?: "Something went wrong", oldState.data) // TODO parse message
                        else -> {
                            Timber.d("Unhandled action in FAILED case: [${operation.action}]")
                            oldState
                        }
                    }
                }
                is Operation.CompletedSuccessfully -> {
                    when (operation.action) {
                        is SearchAction -> {
                            val searchAction = operation.action as SearchAction
                            val operationResult = operation.result as SearchResult
                            val uiItems = mutableListOf<SearchItemUiModel>()
                            uiItems.addAll((oldState.data as SearchUiModel).searchResults)
                            operationResult.items.forEach {
                                uiItems.add(it.toUiModel())
                            }
                            UiState.SuccessState(SearchUiModel(searchAction.query, operationResult.page, uiItems.toList()))
                        }
                        else -> {
                            Timber.d("Unhandled action in SUCCESS case: [${operation.action}]")
                            oldState
                        }
                    }
                }
            }
        }
    }
}