package br.com.xpchallenge.home

import androidx.annotation.VisibleForTesting
import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.presentation.favorite.FavoritePresenter
import br.com.xpchallenge.presentation.mapper.ICharacterViewObjectMapper
import br.com.xpchallenge.presentation.extensions.applyLoadingBehavior
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@ActivityRetainedScoped
class HomePresenter @Inject constructor(
    private val mapper: ICharacterViewObjectMapper<Character>
) : FavoritePresenter<HomeContract.View>(), HomeContract.Presenter {

    @VisibleForTesting
    var paginationOffset = 0
    @VisibleForTesting
    var isLastPage = false

    private var _loadCharactersDisposable: Disposable? = null

    override fun loadCharacters(search: String?) {
        if (isLastPage) return

        if (_loadCharactersDisposable?.isDisposed == false) {
            _loadCharactersDisposable?.dispose()
        }

        _loadCharactersDisposable =
            repository.getCharacters(search = search, paginationOffset = paginationOffset)
                .applyDefaultSchedulers()
                .applyLoadingBehavior(view)
                .subscribeBy(
                    onSuccess = { result ->
                        paginationOffset += result.count
                        isLastPage = result.count < PAGINATION_LIMIT
                        if (result.characters.isEmpty()) {
                            view?.showEmptyState()
                        } else {
                            view?.hideEmptyState()
                            view?.showCharacters(result.characters.map { mapper.map(it) }.filter {
                                it.isImageAvailable
                            })
                        }
                    },

                    onError = { error ->
                        handleError(error = error, retryAction = { loadCharacters(search) })
                    }
                )

        addDisposable { _loadCharactersDisposable }
    }

    override fun loadFavorites() {
        addDisposable {
            repository.getFavoriteCharacters()
                .applyDefaultSchedulers()
                .subscribeBy(
                    onNext = { characters ->
                        if (characters.isEmpty()) {
                            view?.showEmptyState()
                        } else {
                            view?.hideEmptyState()
                        }
                        view?.showCharacters(characters.map { mapper.map(it) })
                    },

                    onError = {
                        handleError(error = it, retryAction = { loadFavorites() })
                    }
                )
        }
    }

    override fun resetPage() {
        this.isLastPage = false
        this.paginationOffset = 0
    }

    companion object {
        private const val PAGINATION_LIMIT = 20
    }
}