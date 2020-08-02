package br.com.xpchallenge.home

import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.repository.ICharacterRepository
import br.com.xpchallenge.ui.core.BasePresenter
import br.com.xpchallenge.ui.extensions.applyLoadingBehavior
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@ActivityRetainedScoped
class HomePresenter @Inject constructor(
    private val repository: ICharacterRepository
) : BasePresenter<HomeContract.View>(),
    HomeContract.Presenter {

    var paginationOffset = 0
    var isLastPage = false

    private var loadCharactersSubscription: Disposable? = null

    override fun loadCharacters(search: String?) {
        if (isLastPage) return

        if (loadCharactersSubscription?.isDisposed == false) {
            loadCharactersSubscription?.dispose()
        }

        loadCharactersSubscription =
            repository.getCharacters(name = search, paginationOffset = paginationOffset)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.main())
                .applyLoadingBehavior(view)
                .subscribeBy(
                    onSuccess = { result ->
                        paginationOffset += result.count
                        isLastPage = result.count < PAGINATION_LIMIT
                        view?.showCharacters(result.characters)
                    },

                    onError = { error ->
                        handleError(error = error, retryAction = { loadCharacters(search) })
                    }
                )

        addDisposable { loadCharactersSubscription }
    }

    override fun loadFavorites() {
        addDisposable {
            repository.getFavoriteCharacters()
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.main())
                .subscribeBy(
                    onNext = {
                        view?.showCharacters(it)
                    },

                    onError = {
                        view?.showError(message = R.string.no_internet_message, retryAction = {
                            loadFavorites()
                        })
                    }
                )
        }
    }

    override fun updateFavorite(character: Character) {
        addDisposable {
            repository.updateFavorite(character)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.main())
                .subscribe()
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