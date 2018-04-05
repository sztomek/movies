package hu.sztomek.movies.presentation.common

interface MessageHolder {

    val message: String

}

sealed class UiError : MessageHolder {

    data class FormValidationUiError(override val message: String) : UiError()
    data class GeneralUiError(override val message: String) : UiError()

}