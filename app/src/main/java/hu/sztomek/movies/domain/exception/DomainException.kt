package hu.sztomek.movies.domain.exception

sealed class DomainException(message: String) : Throwable(message) {

    class FormValidationException(message: String) : DomainException(message)
    class GeneralDomainException(message: String) : DomainException(message)

}