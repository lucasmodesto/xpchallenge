package br.com.xpchallenge.presentation.favorite

import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.domain.repository.ICharacterRepository
import br.com.xpchallenge.presentation.CharacterViewObject
import br.com.xpchallenge.presentation.mapper.ICharacterViewObjectMapper
import br.com.xpchallenge.ui.core.BasePresenter
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

open class FavoritePresenter<T : FavoriteContract.View> :
    BasePresenter<T>(), FavoriteContract.Presenter {

    @Inject
    lateinit var repository: ICharacterRepository

    @Inject
    lateinit var characterViewObjectMapper: ICharacterViewObjectMapper<Character>

    override fun toggleFavorite(character: CharacterViewObject) {
        character.isFavorite = !character.isFavorite
        repository.updateFavorite(character = character.mapToEntity())
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribe()
    }

    override fun subscribeToFavorites() {
        repository.getFavoriteCharacters()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.main())
            .subscribeBy(
                onNext = { characters ->
                    view?.updateFavorites(characters = characters.map {
                        characterViewObjectMapper.map(it)
                    })
                },

                onError = {
                    handleError(error = it, retryAction = { subscribeToFavorites() })
                }
            )
    }
}