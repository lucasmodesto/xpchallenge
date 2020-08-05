package br.com.xpchallenge.home

import br.com.xpchallenge.presentation.core.IBasePresenter
import br.com.xpchallenge.presentation.favorite.FavoriteContract
import br.com.xpchallenge.presentation.model.CharacterViewObject

object HomeContract {

    interface SearchCharactersView : FavoriteContract.View {
        fun showLoading()
        fun hideLoading()
        fun showCharacters(characters: List<CharacterViewObject>)
        fun showEmptyState()
        fun hideEmptyState()
    }

    interface FavoritesView : FavoriteContract.View

    interface SearchCharactersPresenter : IBasePresenter<SearchCharactersView>, FavoriteContract.Presenter {
        fun loadCharacters(query: String? = null)
        fun resetPage()
    }

    interface FavoritesPresenter : IBasePresenter<FavoritesView>, FavoriteContract.Presenter
}