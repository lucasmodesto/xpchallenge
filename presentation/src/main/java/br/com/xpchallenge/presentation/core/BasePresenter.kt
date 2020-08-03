package br.com.xpchallenge.presentation.core

import br.com.xpchallenge.domain.NetworkError
import br.com.xpchallenge.presentation.R
import br.com.xpchallenge.presentation.core.rx.ISchedulersProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

open class BasePresenter<T : BaseView> : IBasePresenter<T> {

    var view: T? = null
    private val _compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var schedulersProvider: ISchedulersProvider

    override fun attach(view: T) {
        this.view = view
    }

    override fun detach() {
        if (!_compositeDisposable.isDisposed) _compositeDisposable.clear()
        this.view = null
    }

    override fun addDisposable(block: () -> Disposable?) {
        this._compositeDisposable.add(block.invoke())
    }

    override fun handleError(error: Throwable, retryAction: () -> Unit) {
        val message = when (error) {
            is NetworkError.NotConnected, is NetworkError.Canceled -> R.string.no_internet_message
            is NetworkError.SlowConnection -> R.string.slow_internet_message
            else -> R.string.unknown_error_message
        }
        view?.showError(message, retryAction)
    }

    fun <T> Single<T>.applyDefaultSchedulers(): Single<T> {
        return this
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
    }

    fun <T> Observable<T>.applyDefaultSchedulers(): Observable<T> {
        return this
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
    }


}