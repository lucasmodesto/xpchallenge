package br.com.xpchallenge.characterdetail

import br.com.xpchallenge.presentation.model.CharacterViewObject
import br.com.xpchallenge.presentation.favorite.FavoriteContract
import br.com.xpchallenge.presentation.model.MediaViewObject
import br.com.xpchallenge.presentation.core.IBasePresenter

object CharacterDetailContract {

    interface View : FavoriteContract.View {
        fun showDescription(hasDescription: Boolean, description: String)
        fun showImage(imageUrl: String)
        fun showName(name: String)
        fun showComics(comics: List<MediaViewObject>)
        fun showSeries(series: List<MediaViewObject>)
        fun showComicsLoading()
        fun hideComicsLoading()
        fun showSeriesLoading()
        fun hideSeriesLoading()
        fun showComicsEmptyState()
        fun hideComicsEmptyState()
        fun showSeriesEmptyState()
        fun hideSeriesEmptyState()
        fun showComicsErrorState(retryAction: () -> Unit)
        fun showSeriesErrorState(retryAction: () -> Unit)
        fun hideComicsErrorState()
        fun hideSeriesErrorState()
    }

    interface Presenter : IBasePresenter<View>, FavoriteContract.Presenter {
        fun start(character: CharacterViewObject)
        fun loadComics(id: Int)
        fun loadSeries(id: Int)
    }
}