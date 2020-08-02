package br.com.xpchallenge.characterdetail

import br.com.xpchallenge.domain.entity.Character
import br.com.xpchallenge.presentation.CharacterViewObject
import br.com.xpchallenge.presentation.favorite.FavoritePresenter
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class CharacterDetailPresenter @Inject constructor() :
    FavoritePresenter<CharacterDetailContract.View>(), CharacterDetailContract.Presenter {

    private lateinit var _character: Character

    override fun start(character: CharacterViewObject) {
        this._character = character.mapToEntity()
        view?.showName(character.name)
        view?.showDescription(
            hasDescription = character.description.isNotEmpty(),
            description = character.description
        )
        view?.showImage(
            isImageAvailable = character.isImageAvailable,
            imageUrl = character.imageUrl
        )
    }

    override fun loadComics(id: String) {

    }

    override fun loadSeries(id: String) {

    }
}