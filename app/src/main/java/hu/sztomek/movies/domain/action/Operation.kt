package hu.sztomek.movies.domain.action

import hu.sztomek.movies.domain.exception.DomainException
import hu.sztomek.movies.domain.model.DomainModel

interface ActionHolder {
    val action: Action
}

sealed class Operation : ActionHolder {

    data class InProgress(override val action: Action) : Operation()
    data class CompletedSuccessfully(override val action: Action, val result: DomainModel) : Operation()
    data class Failed(override val action: Action, val exception: DomainException) : Operation()

}