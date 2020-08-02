package br.com.xpchallenge.ui.core

import br.com.xpchallenge.ui.R
import br.com.xpchallenge.ui.core.rx.ISchedulersProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.net.UnknownHostException
import javax.inject.Inject

open class BasePresenter<T : BaseView> : IBasePresenter<T> {

    protected var view: T? = null
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
        val message = when(error) {

            is UnknownHostException -> R.string.no_internet_message
            else -> R.string.unknown_error_message
        }
        view?.showError(message, retryAction)
    }

}