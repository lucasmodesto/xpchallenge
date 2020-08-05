package br.com.xpchallenge.home

import androidx.annotation.VisibleForTesting
import br.com.xpchallenge.presentation.favorite.FavoritePresenter
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@ActivityRetainedScoped
class SearchCharactersPresenter @Inject constructor() :
    FavoritePresenter<HomeContract.SearchCharactersView>(),
    HomeContract.SearchCharactersPresenter {

    @VisibleForTesting
    var paginationOffset = 0

    @VisibleForTesting
    var isLastPage = false

    private var _loadCharactersDisposable: Disposable? = null

    override fun loadCharacters(query: String?) {
        if (isLastPage) return

        if (_loadCharactersDisposable?.isDisposed == false) {
            _loadCharactersDisposable?.dispose()
        }

        _loadCharactersDisposable =
            repository.getCharacters(query = query, paginationOffset = paginationOffset)
                .applyDefaultSchedulers()
                .doOnSubscribe { view?.showLoading() }
                .doOnTerminate { view?.hideLoading() }
                .subscribeBy(
                    onSuccess = { result ->
                        paginationOffset += result.count
                        isLastPage = result.count < PAGINATION_LIMIT
                        if (result.characters.isEmpty()) {
                            view?.showEmptyState()
                        } else {
                            view?.hideEmptyState()
                            view?.showCharacters(
                                result.characters.map { characterViewObjectMapper.map(it) })
                        }
                    },

                    onError = { error ->
                        handleError(error = error, retryAction = { loadCharacters(query) })
                    }
                )

        addDisposable { _loadCharactersDisposable }
    }

    override fun resetPage() {
        this.isLastPage = false
        this.paginationOffset = 0
    }

    private companion object {
        private const val PAGINATION_LIMIT = 20
    }
}