package br.com.xpchallenge.characterdetail.router

import android.content.Context
import br.com.xpchallenge.characterdetail.CharacterDetailActivity
import br.com.xpchallenge.presentation.model.CharacterViewObject
import br.com.xpchallenge.router.IRoute
import br.com.xpchallenge.router.RouteData
import javax.inject.Inject

class CharacterDetailRouter @Inject constructor() : IRoute<RouteData.CharacterDetailData> {

    override fun open(context: Context?, parameters: RouteData.CharacterDetailData) {
        context?.startActivity(
            CharacterDetailActivity.newIntent(context = context, character =
            parameters.character.run {
                CharacterViewObject(
                    id = this.id,
                    name = this.name,
                    imageUrl = this.imageUrl,
                    isImageAvailable = this.isImageAvailable,
                    description = this.description,
                    isFavorite = this.isFavorite
                )
            })
        )
    }
}