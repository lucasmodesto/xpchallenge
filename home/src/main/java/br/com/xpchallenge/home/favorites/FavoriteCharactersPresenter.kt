package br.com.xpchallenge.home.favorites

import br.com.xpchallenge.home.CharacterViewObject
import br.com.xpchallenge.ui.core.BasePresenter
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class FavoriteCharactersPresenter @Inject constructor() :
    BasePresenter<FavoriteCharactersContract.View>(), FavoriteCharactersContract.Presenter {

    override fun loadFavoriteCharacters() {
        // TODO
    }

    override fun setFavorite(character: CharacterViewObject) {
        // TODO
    }


}