package br.com.xpchallenge.home.favorites

import br.com.xpchallenge.home.CharacterViewObject
import br.com.xpchallenge.ui.core.BaseView
import br.com.xpchallenge.ui.core.IBasePresenter

object FavoriteCharactersContract {

    interface View : BaseView {
        fun showFavoriteCharacters(characters: List<CharacterViewObject>)
    }

    interface Presenter : IBasePresenter<View> {
        fun loadFavoriteCharacters()
        fun setFavorite(character: CharacterViewObject)
    }
}