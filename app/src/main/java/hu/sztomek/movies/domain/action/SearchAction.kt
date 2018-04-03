package hu.sztomek.movies.domain.action

data class SearchAction(val query: String, val page: Int): Action