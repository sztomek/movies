package hu.sztomek.movies.domain

import hu.sztomek.movies.domain.action.Action
import hu.sztomek.movies.domain.model.DomainModel

interface ActionHolder {
    val action: Action
}

sealed class Operation : ActionHolder {

    data class InProgress(override val action: Action) : Operation()
    data class CompletedSuccessfully(override val action: Action, val result: DomainModel) : Operation()
    data class Failed(override val action: Action, val throwable: Throwable) : Operation()

}