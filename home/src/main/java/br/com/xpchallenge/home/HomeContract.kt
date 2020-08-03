package br.com.xpchallenge.home

import br.com.xpchallenge.presentation.model.CharacterViewObject
import br.com.xpchallenge.presentation.favorite.FavoriteContract
import br.com.xpchallenge.presentation.core.BaseView
import br.com.xpchallenge.presentation.core.IBasePresenter

object HomeContract {

    interface View : FavoriteContract.View {
        fun showCharacters(characters: List<CharacterViewObject>)
        fun showEmptyState()
        fun hideEmptyState()
    }

    interface Presenter : IBasePresenter<View>, FavoriteContract.Presenter {
        fun loadCharacters(search: String? = null)
        fun loadFavorites()
        fun resetPage()
    }
}