package br.com.xpchallenge.presentation.favorite

import br.com.xpchallenge.presentation.model.CharacterViewObject
import br.com.xpchallenge.presentation.core.BaseView

object FavoriteContract {

    interface View : BaseView {
        fun updateFavorites(characters: List<CharacterViewObject>)
        fun showEmptyFavorites()
        fun hideEmptyFavorites()
    }

    interface Presenter {
        fun toggleFavorite(character: CharacterViewObject)
        fun subscribeToFavorites()
    }
}