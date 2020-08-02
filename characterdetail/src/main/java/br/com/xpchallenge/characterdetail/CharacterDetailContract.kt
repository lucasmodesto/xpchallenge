package br.com.xpchallenge.characterdetail

import br.com.xpchallenge.presentation.CharacterViewObject
import br.com.xpchallenge.presentation.favorite.FavoriteContract
import br.com.xpchallenge.ui.core.IBasePresenter

object CharacterDetailContract {

    interface View : FavoriteContract.View {
        fun showDescription(hasDescription: Boolean, description: String)
        fun showImage(isImageAvailable: Boolean, imageUrl: String)
        fun showName(name: String)
    }

    interface Presenter : IBasePresenter<View>, FavoriteContract.Presenter {
        fun start(character: CharacterViewObject)
        fun loadComics(id: String)
        fun loadSeries(id: String)
    }
}