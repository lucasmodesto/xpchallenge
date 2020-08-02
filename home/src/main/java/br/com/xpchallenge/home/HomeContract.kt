package br.com.xpchallenge.home

import br.com.xpchallenge.presentation.CharacterViewObject
import br.com.xpchallenge.presentation.favorite.FavoriteContract
import br.com.xpchallenge.ui.core.BaseView
import br.com.xpchallenge.ui.core.IBasePresenter

object HomeContract {

    interface View : FavoriteContract.View, BaseView {
        fun showCharacters(characters: List<CharacterViewObject>)
        fun clearSearch()
    }

    interface Presenter : IBasePresenter<View>, FavoriteContract.Presenter {
        fun loadCharacters(search: String? = null)
        fun loadFavorites()
        fun resetPage()
    }
}