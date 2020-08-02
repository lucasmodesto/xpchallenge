package br.com.xpchallenge.home

import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.repository.ICharacterRepository
import br.com.xpchallenge.presentation.favorite.FavoritePresenter
import br.com.xpchallenge.presentation.mapper.ICharacterViewObjectMapper
import br.com.xpchallenge.ui.extensions.applyLoadingBehavior
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@ActivityRetainedScoped
class HomePresenter @Inject constructor(
    private val mapper: ICharacterViewObjectMapper<Character>
) : FavoritePresenter<HomeContract.View>(), HomeContract.Presenter {

    private var _paginationOffset = 0
    private var _isLastPage = false
    private var _characters: MutableList<Character> = mutableListOf()
    private var _loadCharactersDisposable: Disposable? = null

    override fun loadCharacters(search: String?) {
        if (_isLastPage) return

        if (_loadCharactersDisposable?.isDisposed == false) {
            _loadCharactersDisposable?.dispose()
        }

        _loadCharactersDisposable =
            repository.getCharacters(name = search, paginationOffset = _paginationOffset)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.main())
                .applyLoadingBehavior(view)
                .subscribeBy(
                    onSuccess = { result ->
                        _characters.addAll(result.characters)
                        _paginationOffset += result.count
                        _isLastPage = result.count < PAGINATION_LIMIT
                        view?.showCharacters(result.characters.map { mapper.map(it) })
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
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.main())
                .subscribeBy(
                    onNext = { characters ->
                        view?.showCharacters(characters.map { mapper.map(it) })
                    },

                    onError = {
                        handleError(error = it, retryAction = { loadFavorites() })
                    }
                )
        }
    }

    override fun resetPage() {
        this._characters.clear()
        this._isLastPage = false
        this._paginationOffset = 0
        view?.clearSearch()
    }

    companion object {
        private const val PAGINATION_LIMIT = 20
    }
}