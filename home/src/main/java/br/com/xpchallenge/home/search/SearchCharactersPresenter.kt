package br.com.xpchallenge.home.search

import android.util.Log
import br.com.xpchallenge.domain.repository.ICharacterRepository
import br.com.xpchallenge.home.CharacterViewObject
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
                        view?.showCharacters(characters.map {
                            CharacterViewObject(
                                name = it.name,
                                imageUrl = it.name,
                                isFavorite = false
                            )
                        })
                    },

                    onError = {
                        Log.e(this::class.simpleName, "onError", it)
                        // TODO: handle error
                    }
                )
        }
    }


}