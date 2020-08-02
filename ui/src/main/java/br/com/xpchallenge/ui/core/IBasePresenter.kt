package br.com.xpchallenge.ui.core

import io.reactivex.rxjava3.disposables.Disposable

interface IBasePresenter<T : BaseView> {
    fun attach(view: T)
    fun detach()
    fun addDisposable(block: () -> Disposable?)
    fun handleError(error: Throwable, retryAction: () -> Unit)
}