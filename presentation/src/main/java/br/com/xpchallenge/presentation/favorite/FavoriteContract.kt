package br.com.xpchallenge.presentation.favorite

import br.com.xpchallenge.presentation.CharacterViewObject
import br.com.xpchallenge.ui.core.BaseView

object FavoriteContract {

    interface View : BaseView {
        fun updateFavorites(characters: List<CharacterViewObject>)
    }

    interface Presenter {
        fun toggleFavorite(character: CharacterViewObject)
        fun subscribeToFavorites()
    }
}