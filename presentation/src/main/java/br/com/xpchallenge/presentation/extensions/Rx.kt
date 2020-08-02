package br.com.xpchallenge.presentation.extensions

import br.com.xpchallenge.presentation.core.BaseView
import io.reactivex.rxjava3.core.Single

fun <T, R: BaseView> Single<T>.applyLoadingBehavior(view: R?): Single<T> {
    return this
        .doOnSubscribe { view?.showLoading() }
        .doOnTerminate { view?.hideLoading() }
}