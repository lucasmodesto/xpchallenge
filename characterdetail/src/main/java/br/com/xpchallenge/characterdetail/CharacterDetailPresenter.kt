package br.com.xpchallenge.characterdetail

import br.com.xpchallenge.domain.entity.Comic
import br.com.xpchallenge.domain.entity.Series
import br.com.xpchallenge.presentation.CharacterViewObject
import br.com.xpchallenge.presentation.favorite.FavoritePresenter
import br.com.xpchallenge.presentation.mapper.IMediaViewObjectMapper
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@ActivityRetainedScoped
class CharacterDetailPresenter @Inject constructor(
    private val comicsMapper: IMediaViewObjectMapper<Comic>,
    private val seriesMapper: IMediaViewObjectMapper<Series>
) : FavoritePresenter<CharacterDetailContract.View>(), CharacterDetailContract.Presenter {

    override fun start(character: CharacterViewObject) {
        view?.showName(character.name)
        view?.showDescription(
            hasDescription = character.description.isNotEmpty(),
            description = character.description
        )
        view?.showImage(imageUrl = character.imageUrl)
    }

    override fun loadComics(id: Int) {
        addDisposable {
            repository.getComics(id)
                .applyDefaultSchedulers()
                .doOnSubscribe {
                    view?.showComicsLoading()
                    view?.hideComicsErrorState()
                }
                .doOnTerminate { view?.hideComicsLoading() }
                .subscribeBy(
                    onSuccess = {
                        if (it.isEmpty()) {
                            view?.showComicsEmptyState()
                        } else {
                            view?.hideComicsEmptyState()
                            view?.showComics(it.map { comicsItem -> comicsMapper.map(comicsItem) })
                        }
                    },

                    onError = {
                        view?.showComicsErrorState {
                            loadComics(id)
                        }
                    }
                )
        }
    }

    override fun loadSeries(id: Int) {
        addDisposable {
            repository.getSeries(id)
                .applyDefaultSchedulers()
                .doOnSubscribe {
                    view?.hideSeriesErrorState()
                    view?.showSeriesLoading()
                }
                .doOnTerminate { view?.hideSeriesLoading() }
                .subscribeBy(
                    onSuccess = {
                        view?.hideSeriesErrorState()
                        if (it.isEmpty()) {
                            view?.showSeriesEmptyState()
                        } else {
                            view?.hideSeriesEmptyState()
                            view?.showSeries(it.map { seriesItem -> seriesMapper.map(seriesItem) })
                        }
                    },

                    onError = {
                        view?.showSeriesErrorState {
                            loadSeries(id)
                        }
                    }
                )
        }
    }
}