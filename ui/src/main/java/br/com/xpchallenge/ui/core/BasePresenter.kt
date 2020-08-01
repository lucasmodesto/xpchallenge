package br.com.xpchallenge.ui.core

import br.com.xpchallenge.ui.core.rx.ISchedulersProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
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

    override fun addDisposable(block: () -> Disposable) {
        this._compositeDisposable.add(block.invoke())
    }

}