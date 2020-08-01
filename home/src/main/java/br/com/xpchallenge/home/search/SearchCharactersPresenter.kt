package br.com.xpchallenge.home.search

import android.util.Log
import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.repository.ICharacterRepository
import br.com.xpchallenge.ui.core.BasePresenter
import br.com.xpchallenge.ui.extensions.applyLoadingBehavior
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@ActivityRetainedScoped
class SearchCharactersPresenter @Inject constructor(
    private val repository: ICharacterRepository
) : BasePresenter<SearchCharactersContract.View>(), SearchCharactersContract.Presenter {

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

    override fun onFavoriteChange(character: Character) {
        addDisposable {
            repository.updateFavorite(character)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.main())
                .subscribeBy(

                    onComplete = {

                    },

                    onError = {

                    }
                )
        }
    }

}