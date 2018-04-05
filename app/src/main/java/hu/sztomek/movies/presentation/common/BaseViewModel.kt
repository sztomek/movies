package hu.sztomek.movies.presentation.common

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import hu.sztomek.movies.domain.action.Operation
import hu.sztomek.movies.domain.action.Action
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    private val actionStream = PublishSubject.create<Action>()
    private val disposables = CompositeDisposable()
    private var subscribedToActions = false

    val stateStream: MutableLiveData<UiState> = MutableLiveData()

    override fun onCleared() {
        disposables.dispose()
        subscribedToActions = false
        super.onCleared()
    }


    fun takeInitialState(initialState: UiState) {
        if (subscribedToActions) {
            throw IllegalStateException("Already called takeInitialState!")
        }

        stateStream.value = initialState

        disposables.add(
                actionStream
                        .observeOn(Schedulers.computation())
                        .compose(invokeActions())
                        .scan(stateStream.value!!, getReducerFunction())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    stateStream.value = it
                                },
                                {
                                    Timber.d(it)
                                }
                        )
        )
        subscribedToActions = true
    }

    fun sendAction(action: Action) {
        actionStream.onNext(action)
    }

    protected abstract fun invokeActions(): ObservableTransformer<Action, Operation>
    protected abstract fun getReducerFunction(): BiFunction<UiState, in Operation, UiState>
}