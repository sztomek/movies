package hu.sztomek.movies.presentation.common

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import hu.sztomek.movies.presentation.model.UiModel
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity<M : UiModel> : AppCompatActivity() {

    private companion object {
        private const val KEY_STATE = "movies.state"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(
                this,
                viewModelFactory
        ).get(getViewModelClass())
        viewModel.takeInitialState(UiState.IdleState(getInitialState(savedInstanceState)))
        viewModel.stateStream.observe(
                this,
                Observer {
                    Timber.d("new state: [$it]")
                    render(it)
                }
        )

        initUi()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        persistState(outState)
    }

    protected fun getInitialState(savedInstanceState: Bundle?): M {
        return savedInstanceState?.getParcelable(KEY_STATE) ?: getDefaultInitialState()
    }

    protected fun persistState(outBundle: Bundle?) {
        viewModel.stateStream.value?.data?.let {
            outBundle?.putParcelable(KEY_STATE, it)
        }
    }

    protected abstract fun initUi()
    protected abstract fun getDefaultInitialState(): M
    protected abstract fun getViewModelClass(): Class<out BaseViewModel>
    protected abstract fun render(it: UiState?)
}
