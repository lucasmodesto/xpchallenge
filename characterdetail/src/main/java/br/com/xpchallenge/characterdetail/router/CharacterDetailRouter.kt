package br.com.xpchallenge.characterdetail.router

import android.content.Context
import br.com.xpchallenge.characterdetail.CharacterDetailActivity
import br.com.xpchallenge.presentation.model.CharacterViewObject
import br.com.xpchallenge.router.CharacterDetailRouteData
import br.com.xpchallenge.router.IRoute
import javax.inject.Inject

class CharacterDetailRouter @Inject constructor() : IRoute<CharacterDetailRouteData> {

    override fun open(context: Context?, parameters: CharacterDetailRouteData) {
        context?.startActivity(
            CharacterDetailActivity.newIntent(
                context = context,
                character = parameters.character.run {
                    CharacterViewObject(
                        id = this.id,
                        name = this.name,
                        imageUrl = this.imageUrl,
                        isImageAvailable = this.isImageAvailable,
                        description = this.description,
                        isFavorite = this.isFavorite

                    )
                }
            )
        )
    }
}
