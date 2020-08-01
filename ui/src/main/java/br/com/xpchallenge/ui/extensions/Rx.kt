package br.com.xpchallenge.ui.extensions

import br.com.xpchallenge.ui.core.BaseView
import io.reactivex.rxjava3.core.Single

fun <T, R: BaseView> Single<T>.applyLoadingBehavior(view: R?): Single<T> {
    return this
        .doOnSubscribe { view?.showLoading() }
        .doOnTerminate { view?.hideLoading() }
}