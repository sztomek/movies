package hu.sztomek.movies.presentation.screen.details

import hu.sztomek.movies.R
import hu.sztomek.movies.domain.action.Action
import hu.sztomek.movies.domain.action.GetDetailsAction
import hu.sztomek.movies.domain.action.Operation
import hu.sztomek.movies.domain.exception.DomainException
import hu.sztomek.movies.domain.model.details.MovieDetails
import hu.sztomek.movies.domain.resource.ResourceHelper
import hu.sztomek.movies.domain.usecase.DetailsUseCase
import hu.sztomek.movies.presentation.common.BaseViewModel
import hu.sztomek.movies.presentation.common.UiError
import hu.sztomek.movies.presentation.common.UiState
import hu.sztomek.movies.presentation.converter.toUiModel
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsViewModel @Inject constructor(
        private val getDetailsUseCase: DetailsUseCase,
        private val resourceHelper: ResourceHelper
) : BaseViewModel() {

    override fun invokeActions(): ObservableTransformer<Action, Operation> {
        return ObservableTransformer { upstream ->
            upstream
                    .ofType(GetDetailsAction::class.java)
                    .flatMap {
                        getDetailsUseCase.execute(it, ObservableTransformer { it.observeOn(Schedulers.computation()) })
                                .map { domainModel ->
                                    Operation.CompletedSuccessfully(it, domainModel) as Operation
                                }
                                .startWith(Operation.InProgress(it))
                                .onErrorReturn { t ->
                                    Operation.Failed(
                                            it,
                                            DomainException.GeneralDomainException(
                                                    t.message ?: resourceHelper.getString(R.string.error_unknown))
                                    )
                                }
                    }
        }
    }

    override fun getReducerFunction(): BiFunction<UiState, in Operation, UiState> {
        return BiFunction { oldState, operation ->
            when (operation) {
                is Operation.InProgress -> {
                    UiState.LoadingState(
                            resourceHelper.getString(R.string.label_please_wait),
                            oldState.data
                    )
                }
                is Operation.Failed -> {
                    UiState.ErrorState(
                            UiError.GeneralUiError(
                                    operation.exception.message ?: resourceHelper.getString(R.string.error_unknown)
                            ),
                            oldState.data
                    )
                }
                is Operation.CompletedSuccessfully -> {
                    UiState.IdleState(
                            (operation.result as MovieDetails).toUiModel()
                    )
                }
            }
        }
    }
}