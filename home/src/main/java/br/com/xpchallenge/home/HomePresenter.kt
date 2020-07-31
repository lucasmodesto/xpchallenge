package br.com.xpchallenge.home

import android.util.Log
import br.com.xpchallenge.domain.repository.ICharacterRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor(val repository: ICharacterRepository) {

    fun fetchCharacters() {
        repository.getCharacters()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(

                onSuccess = {
                    Log.e(this::class.simpleName, it.toString())
                },

                onError = {
                    Log.e(this::class.simpleName, "onError", it)
                }
            )

    }
}