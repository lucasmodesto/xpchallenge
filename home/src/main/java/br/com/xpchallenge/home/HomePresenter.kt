package br.com.xpchallenge.home

import android.util.Log
import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.repository.ICharacterRepository
import br.com.xpchallenge.ui.core.BasePresenter
import br.com.xpchallenge.ui.extensions.applyLoadingBehavior
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@ActivityRetainedScoped
class HomePresenter @Inject constructor(
    private val repository: ICharacterRepository
) : BasePresenter<HomeContract.View>(),
    HomeContract.Presenter {

    override fun loadCharacters(search: String?) {
        addDisposable {
            repository.getCharacters()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.main())
                .applyLoadingBehavior(view)
                .subscribeBy(
                    onSuccess = { characters ->
                        view?.showCharacters(characters)
                    },

                    onError = { error ->
                        Log.e(this::class.simpleName, "onError", error)
                        // TODO: handle error
                    }
                )
        }
    }

    override fun loadFavorites() {
       addDisposable {
           repository.getFavoriteCharacters()
               .subscribeOn(schedulersProvider.io())
               .observeOn(schedulersProvider.main())
               .subscribeBy(
                   onNext = {
                       view?.showCharacters(it)
                   },

                   onError = {
                       // TODO: handle error
                   }
               )
       }
    }

    override fun updateFavorite(character: Character) {
        addDisposable {
            repository.updateFavorite(character)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.main())
                .subscribe()
        }
    }
}