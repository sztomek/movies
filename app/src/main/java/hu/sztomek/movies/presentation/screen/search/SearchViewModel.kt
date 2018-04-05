package hu.sztomek.movies.presentation.screen.search

import hu.sztomek.movies.R
import hu.sztomek.movies.domain.action.Action
import hu.sztomek.movies.domain.action.Operation
import hu.sztomek.movies.domain.action.SearchAction
import hu.sztomek.movies.domain.exception.DomainException
import hu.sztomek.movies.domain.model.search.SearchResult
import hu.sztomek.movies.domain.resource.ResourceHelper
import hu.sztomek.movies.domain.usecase.SearchUseCase
import hu.sztomek.movies.presentation.common.BaseViewModel
import hu.sztomek.movies.presentation.common.UiError
import hu.sztomek.movies.presentation.common.UiState
import hu.sztomek.movies.presentation.converter.toUiModel
import hu.sztomek.movies.presentation.model.SearchItemUiModel
import hu.sztomek.movies.presentation.model.SearchUiModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val searchUseCase: SearchUseCase,
        private val resourceHelper: ResourceHelper
) : BaseViewModel() {

    override fun invokeActions(): ObservableTransformer<Action, Operation> {
        return ObservableTransformer { upstream ->
            upstream.ofType(SearchAction::class.java)
                    .flatMap {
                        if (it.query.trim().isEmpty()) {
                            Observable.just(
                                    Operation.Failed(
                                            it,
                                            DomainException.FormValidationException(
                                                    resourceHelper.getString(R.string.error_query_empty)
                                            )
                                    )
                            )
                        } else {
                            searchUseCase.execute(it, ObservableTransformer { it.observeOn(Schedulers.computation()) })
                                    .map { res -> Operation.CompletedSuccessfully(it, res) as Operation }
                                    .onErrorReturn { t ->
                                        Operation.Failed(
                                                it,
                                                DomainException.GeneralDomainException(
                                                        t.message ?: resourceHelper.getString(R.string.error_unknown)
                                                )
                                        )
                                    }
                                    .startWith(Operation.InProgress(it))
                        }
                    }
        }
    }

    override fun getReducerFunction(): BiFunction<UiState, in Operation, UiState> {
        return BiFunction { oldState: UiState, operation: Operation ->
            when (operation) {
                is Operation.InProgress -> UiState.LoadingState(resourceHelper.getString(R.string.label_please_wait), oldState.data)
                is Operation.Failed -> {
                    when (operation.action) {
                        is SearchAction -> {
                            val exception = operation.exception
                            val uiError: UiError = when (exception) {
                                is DomainException.FormValidationException -> {
                                    UiError.FormValidationUiError(exception.message!!)
                                }
                                else -> {
                                    UiError.GeneralUiError(exception.message ?: resourceHelper.getString(R.string.error_unknown))
                                }
                            }

                            UiState.ErrorState(
                                    uiError,
                                    oldState.data)
                        }
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
                            if (searchAction.page > 1) {
                                uiItems.addAll((oldState.data as SearchUiModel).searchResults)
                            }
                            operationResult.items.forEach {
                                uiItems.add(it.toUiModel())
                            }
                            UiState.IdleState(
                                    SearchUiModel(
                                            searchAction.query,
                                            operationResult.page,
                                            operationResult.totalPages,
                                            if (uiItems.isEmpty())
                                                resourceHelper.getString(R.string.label_nothing_to_show)
                                            else
                                                resourceHelper.getStringFormatted(
                                                        R.string.format_showing_results,
                                                        arrayOf(operationResult.page.toString(), operationResult.totalPages.toString())),
                                            uiItems.toList()
                                    )
                            )
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