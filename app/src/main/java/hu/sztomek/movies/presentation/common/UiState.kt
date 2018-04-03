package hu.sztomek.movies.presentation.common

import hu.sztomek.movies.presentation.model.UiModel

interface DataHolder {
    val data: UiModel
}

sealed class UiState : DataHolder {

    data class IdleState(override val data: UiModel): UiState()
    data class LoadingState(val message: String, override val data: UiModel): UiState()
    data class ErrorState(val message: String, override val data: UiModel): UiState()
    data class SuccessState(override val data: UiModel): UiState()

}